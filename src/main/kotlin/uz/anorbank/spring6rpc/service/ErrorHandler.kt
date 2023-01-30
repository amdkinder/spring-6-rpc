package uz.anorbank.spring6rpc.service

import uz.anorbank.spring6rpc.dummy.JsonRpcRequest
import uz.anorbank.spring6rpc.dummy.JsonRpcResponse
import kotlin.reflect.KClass

interface ErrorHandler {
    /**
     *
     */
    fun getExceptionType(): KClass<out Exception>?

    /**
     *
     */
    fun buildResponse(e: Exception, request: JsonRpcRequest?): JsonRpcResponse
}