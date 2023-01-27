package uz.anorbank.spring6rpc.dummy

import java.lang.reflect.Method


data class JsonRpcServiceInfo (
    var uri: String? = null,
    var instance: Any? = null,
    var methodName: String? = null,
    var method: Method? = null,
    var params: List<JsonRpcParamInfo?>? = null,
    var errors: List<JsonRpcErrorInfo?>? = null,
)