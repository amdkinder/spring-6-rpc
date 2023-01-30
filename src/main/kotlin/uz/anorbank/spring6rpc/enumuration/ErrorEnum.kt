package uz.anorbank.spring6rpc.enumuration

import com.fasterxml.jackson.databind.JsonMappingException
import jakarta.validation.ConstraintViolationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import uz.anorbank.spring6rpc.dummy.JsonRpcRequest
import uz.anorbank.spring6rpc.dummy.JsonRpcResponse
import uz.anorbank.spring6rpc.exception.InvalidParamsException
import uz.anorbank.spring6rpc.exception.InvalidWrapperException
import uz.anorbank.spring6rpc.exception.JsonRpcVersionValidationException
import uz.anorbank.spring6rpc.exception.MethodNotFoundException
import uz.anorbank.spring6rpc.service.ErrorHandler
import uz.anorbank.spring6rpc.util.ErrorHandlerUtils
import kotlin.reflect.KClass


enum class ErrorEnum(
    private var exception: KClass<out Exception>,
    private var message: String? = null,
    private var code: Int = 0
) : ErrorHandler {


    ARGS_VALIDATION(
        exception = ConstraintViolationException::class,
        "The JSON sent is not a valid Request object:",
        -32600
    ),

    METHOD_NOT_FOUND(
        exception = MethodNotFoundException::class,
        code = -32601
    ),
    INVALID_PARAMS(
        exception = InvalidParamsException::class,
        code = -32602
    ),
    INVALID_WRAPPER(
        exception = InvalidWrapperException::class,
        code = -32603
    ),
    JSON_PARSE(
        exception = JsonMappingException::class,
        code = -32700
    ),
    RPC_VERSION_VALIDATION(
        exception = JsonRpcVersionValidationException::class,
        code = -32600
    ),
    COMMON(
        exception = Exception::class,
        code = -32099
    );

    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    override fun buildResponse(e: Exception, request: JsonRpcRequest?): JsonRpcResponse {
        if (message != null)
            return ErrorHandlerUtils.instance!!.buildResponse(request, code, e, getMessage(e))!!
        return ErrorHandlerUtils.instance!!.buildResponse(request, code, e)!!

    }

    private fun getMessage(e: Exception): String {
        return "$message ${e.message}"
    }


    override fun getExceptionType(): KClass<out Exception>? {
        return exception
    }

}