package uz.anorbank.spring6rpc.dummy

interface JRpcClassMethod<TResponse> {
    fun invoke(params: Map<String, Any>): TResponse
    fun getMethodName(): String
    fun getParams(): Map<String, Class<out Any>>
    fun getServiceUri(): String
}