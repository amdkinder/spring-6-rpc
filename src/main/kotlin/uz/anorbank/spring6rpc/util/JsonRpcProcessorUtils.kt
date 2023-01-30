package uz.anorbank.spring6rpc.util

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.validation.constraints.NotNull
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContext
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import uz.anorbank.spring6rpc.annotation.*
import uz.anorbank.spring6rpc.dummy.*
import uz.anorbank.spring6rpc.exception.InvalidParamsException
import uz.anorbank.spring6rpc.exception.InvalidWrapperException
import uz.anorbank.spring6rpc.exception.JsonRpcVersionValidationException
import uz.anorbank.spring6rpc.exception.MethodParamsMetaDataException
import uz.anorbank.spring6rpc.service.ErrorHandler
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Parameter
import java.util.*

@Component
class JsonRpcProcessorUtils(
    private val context: ApplicationContext,
    private val objectMapper: ObjectMapper

) {

    private val log = LoggerFactory.getLogger(this.javaClass)

    companion object {
        val EMPTY_ERROR_LIST = mutableListOf<JsonRpcErrorInfo>()
        const val JSON_RPC_VERSION = "2.0"
    }

    @Throws(JsonRpcVersionValidationException::class)
    fun validateRequest(request: JsonRpcRequest) {
        log.trace("validateRequest started")
        if (request.jsonrpc == JSON_RPC_VERSION) {
            return
        }
        log.trace("RPC version validation error occurred")
        throw JsonRpcVersionValidationException(JSON_RPC_VERSION)
    }

    fun getRpcInfoList(): List<JsonRpcServiceInfo?> {
        log.trace("getRpcInfoList started")
        return getRpcImplTypes()
            .map(this::getRpcInfoListByMethod)
            .filter(Objects::nonNull)
            .flatten()
    }

    fun getRpcInfoListByMethod(service: Class<*>): List<JsonRpcServiceInfo?> {
        log.trace("getJsonRpcInfo started")
        val bean = context.getBean(service)
        return service.declaredMethods
            .filter { it.getAnnotation(JRpcMethod::class.java) != null }
            .map { getJsonRpcInfo(bean, it, service) }
            .filter { Objects.nonNull(it) }
    }

    fun getJsonRpcInfo(bean: Any, method: Method, service: Class<*>): JsonRpcServiceInfo? {

        log.trace("getJsonRpcInfo started for bean with name : {}", bean.javaClass.simpleName)

        val methodAnnotation = method.getAnnotation(JRpcMethod::class.java)
        val errorInfos = getErrorInfos(method)
        val params = getParamInfo(method)

        return when (methodAnnotation) {
            null -> null
            else -> JsonRpcServiceInfo(
                uri = service.getAnnotation(JRpcService::class.java).value,
                instance = bean,
                methodName = methodAnnotation.value,
                method = method,
                params = params,
                errors = errorInfos
            )
        }
    }

    fun getErrorInfos(method: Method): List<JsonRpcErrorInfo> {
        log.trace("getErrorInfos started")
        val errors = method.getAnnotation(JRpcErrors::class.java)
            ?: return EMPTY_ERROR_LIST
        return errors.value
            .map { getJsonRpcErrorInfo(it) }
    }


    fun getParamInfo(method: Method): List<JsonRpcParamInfo> {
        log.trace("getAnnotation started")
        return method.parameters
            .map {
                JsonRpcParamInfo(
                    name = getParamName(it),
                    type = it.type,
                    order = it.name
                )
            }
    }

    fun isDesiredMethod(@NotNull request: JsonRpcRequest, rpcInfo: JsonRpcServiceInfo): Boolean {
        log.trace(
            "isDesiredMethod started for request method : {}, system method : {}",
            request.method,
            rpcInfo.methodName
        )
        return rpcInfo.methodName.equals(request.method, true)
    }

    @Throws(IllegalAccessException::class, InvocationTargetException::class, InvalidParamsException::class)
    fun executeMethod(request: JsonRpcRequest, jsonRpcServiceInfo: JsonRpcServiceInfo): Any {
        log.trace(
            "executeMethod started for method : {}, of type : {}",
            jsonRpcServiceInfo.methodName,
            (jsonRpcServiceInfo.instance!!)::class.simpleName
        )
        val params = request.params as LinkedHashMap<*, *>
        if (params.size != jsonRpcServiceInfo.params?.size) {
            throw InvalidParamsException()
        }
        val args = jsonRpcServiceInfo
            .params!!
            .sortedWith(Comparator.comparing { it!!.order!! })
            .map { param -> getParam(params, param!!) }
            .toTypedArray()
        val methodParams = jsonRpcServiceInfo.method!!.parameterTypes
        validateMethodParams(args, methodParams)
        return jsonRpcServiceInfo.method!!.invoke(jsonRpcServiceInfo.instance, *args)
    }

    @Throws(InvalidWrapperException::class)
    fun mapResult(
        request: JsonRpcRequest,
        jsonRpcServiceInfo: JsonRpcServiceInfo?,
        result: Any
    ): ResponseEntity<*> {
        log.trace("mapResult started result of method : {}", jsonRpcServiceInfo?.methodName)
        if (result is JsonRpcResponse)
            ResponseEntity.ok(result)
        val jsonRpcResponse = JsonRpcResponse(
            id = request.id,
            jsonrpc = JSON_RPC_VERSION,
            result = result
        )
        return ResponseEntity.ok(jsonRpcResponse)
    }


    private fun getRpcImplTypes(): Set<Class<*>> {
        log.trace("getRpcImplTypes started")
        return Reflections(getConfigurationBuilder())
            .getTypesAnnotatedWith(JRpcService::class.java)
    }

    private fun getRpcClassMethodTypes(): Set<Class<out JRpcClassMethod<*, *>>> {
        log.trace("getRpcClassMethodTypes started")
        return Reflections(getConfigurationBuilder())
            .getSubTypesOf(JRpcClassMethod::class.java)
    }

    fun getAllErrorHandlers(): List<ErrorHandler> {

        return Reflections(
            ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())
        )
            .getSubTypesOf(ErrorHandler::class.java)
            .filter { clazz -> clazz.enumConstants.isNotEmpty() }
            .map { clazz -> clazz.enumConstants.toList() }
            .flatten<ErrorHandler>()

    }


    private fun getConfigurationBuilder(): ConfigurationBuilder {
        log.trace("getConfigurationBuilder started")
        return ConfigurationBuilder()
            .setScanners(Scanners.TypesAnnotated)
            .setUrls(ClasspathHelper.forJavaClassPath())
    }

    private fun getJsonRpcErrorInfo(rpcError: JRpcError): JsonRpcErrorInfo {
        log.trace("getJsonRpcErrorInfo")
        return JsonRpcErrorInfo(
            rpcError.exception,
            rpcError.code,
            getOptionalString(rpcError.message),
            getOptionalString(rpcError.data)
        )
    }

    private fun getOptionalString(text: String): String? {
        log.trace("getOptionalString started for : {}", text)
        return if (text == "") null else text
    }

    @Throws(Exception::class)
    private fun getParamName(parameter: Parameter): String {
        log.trace("getParamName started for param with type : {}", parameter.type)
        val rpcParam = parameter.getAnnotation(JRpcParam::class.java)
        if (rpcParam == null) {
            log.trace("method parameter name annotation is not set")
            throw MethodParamsMetaDataException()
        }
        return rpcParam.value
    }

    @Throws(InvalidParamsException::class)
    private fun validateMethodParams(args: Array<Any?>, methodParams: Array<Class<*>>) {
        if (args.size != methodParams.size) {
            log.warn("given parameters count not match to target method's. execution terminated")
            throw InvalidParamsException()
        }
    }

    @Throws(Exception::class)
    private fun getParam(params: LinkedHashMap<*, *>, param: JsonRpcParamInfo): Any? {
        log.trace("getParam started")
        if (params[param.name] == null) {
            throw InvalidParamsException()
        }
        return objectMapper.convertValue(params[param.name], param.type)
    }

    private fun mapToJsonRpcResponse(request: JsonRpcRequest, response: Any): JsonRpcResponse {
        log.trace("mapToJsonRpcResponse started")
        return JsonRpcResponse(request.id, JSON_RPC_VERSION, response, null)
    }

}