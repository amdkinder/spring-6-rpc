package uz.anorbank.spring6rpc.dummy


data class JsonRpcParamInfo (
    var name: String? = null,
    var type: Class<*>? = null,
    var order: String? = null,
)