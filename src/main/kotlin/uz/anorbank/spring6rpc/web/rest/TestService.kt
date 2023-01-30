package uz.anorbank.spring6rpc.web.rest

import uz.anorbank.spring6rpc.annotation.JRpcMethod
import uz.anorbank.spring6rpc.annotation.JRpcParam
import uz.anorbank.spring6rpc.annotation.JRpcService

@JRpcService("/api/jsonrpc")
class TestService {

    @JRpcMethod("sum")
    fun sum(@JRpcParam("a") a: Int, @JRpcParam("b") b: Int): Int {
        return a + b
    }
}