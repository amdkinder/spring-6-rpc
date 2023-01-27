package uz.anorbank.spring6rpc.service

import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import uz.anorbank.spring6rpc.dummy.*
import uz.anorbank.spring6rpc.enumuration.ErrorEnum
import uz.anorbank.spring6rpc.util.ErrorHandlerUtils
import uz.anorbank.spring6rpc.util.ReflectionUtils

@Component
class ErrorHandlerContext(
    private val handlers: List<ErrorHandler>,
    private val metaData: RpcServiceMetaData,
    private val reflectionUtil: ReflectionUtils,
    private val handlerUtil: ErrorHandlerUtils
) {
    private val log = LoggerFactory.getLogger(this.javaClass)

    fun handle(@Valid e: Exception?, request: JsonRpcRequest): JsonRpcResponse? {
        log.debug("commonHandler started", e)
        val exception = reflectionUtil.extractError(e!!)
        return metaData.getRpcInfoList()
            .filter { info -> info?.methodName == request.method }
            .flatMap { errors -> jsonRpcErrorInfos(errors, exception) }
            .map { errorInfo -> handlerUtil.getJsonRpcResponse(request, errorInfo!!, exception) }
            .firstOrNull() ?: handlers
            .filter { it.getExceptionType()!! == exception.javaClass }
            .map { it.buildResponse(exception, request) }
            .firstOrNull() ?: ErrorEnum.COMMON.buildResponse(exception, request)
    }

    private fun jsonRpcErrorInfos(
        errors: JsonRpcServiceInfo?,
        exception: Exception
    ): List<JsonRpcErrorInfo?> {
        return errors!!.errors!!.filter { error ->
            error?.exception == exception.javaClass
        }
    }

}