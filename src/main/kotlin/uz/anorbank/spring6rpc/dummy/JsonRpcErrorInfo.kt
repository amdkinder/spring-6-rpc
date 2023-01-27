package uz.anorbank.spring6rpc.dummy

import kotlin.reflect.KClass

data class JsonRpcErrorInfo(
    var exception: KClass<out Throwable>? = null,
    var code: Int = 0,
    var message: String? = null,
    var data: String? = null,
)