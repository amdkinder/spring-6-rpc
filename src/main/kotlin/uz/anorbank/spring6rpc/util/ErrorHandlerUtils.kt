package uz.anorbank.spring6rpc.util

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import uz.anorbank.spring6rpc.dummy.JsonRpcError
import uz.anorbank.spring6rpc.dummy.JsonRpcErrorInfo
import uz.anorbank.spring6rpc.dummy.JsonRpcRequest
import uz.anorbank.spring6rpc.dummy.JsonRpcResponse

@Component
class ErrorHandlerUtils : InitializingBean {
    companion object {
        var instance: ErrorHandlerUtils? = null
    }

    private val log = LoggerFactory.getLogger(this.javaClass)

    fun buildResponse(request: JsonRpcRequest, code: Int, e: Throwable): JsonRpcResponse? {
        log.trace("buildResponse started")
        return JsonRpcResponse(request.id, request.jsonrpc, null, getError(e, code))
    }

    fun buildResponse(request: JsonRpcRequest, code: Int, e: Throwable, message: String): JsonRpcResponse? {
        log.trace("buildResponse started")
        return JsonRpcResponse(request.id, request.jsonrpc, null, getError(e, code, message))
    }

    fun wrapResponse(response: JsonRpcResponse?): ResponseEntity<JsonRpcResponse?> {
        log.trace("wrapResponse started")
        return ResponseEntity.ok(response)
    }

    private fun getError(e: Throwable, code: Int): JsonRpcError {
        log.trace("getError started")
        return JsonRpcError(code, e.message, e.javaClass.simpleName)
    }

    private fun getError(e: Throwable, code: Int, message: String): JsonRpcError {
        log.trace("getError started")
        return JsonRpcError(code, message, e.javaClass.simpleName)
    }

    fun getJsonRpcResponse(
        request: JsonRpcRequest,
        errorInfo: JsonRpcErrorInfo,
        exception: Throwable
    ): JsonRpcResponse? {
        return JsonRpcResponse(request.id, request.jsonrpc, null, getError(errorInfo, exception))
    }

    private fun getError(errorInfo: JsonRpcErrorInfo, exception: Throwable): JsonRpcError {
        return JsonRpcError(
            errorInfo.code,
            getString(errorInfo.message, exception.message),
            getString(errorInfo.data, exception.javaClass.name)
        )
    }

    private fun getString(value: String?, fallback: String?): String? {
        return if (value == null || value == "") fallback else value
    }

    override fun afterPropertiesSet() {
        instance = this;
    }
}