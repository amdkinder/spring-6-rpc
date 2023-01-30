package uz.anorbank.spring6rpc.dummy

interface JRpcClassMethod<TRequest, TResponse> {
    fun invoke(params: TRequest): TResponse
    fun getName(): String
    fun getParams(): Map<String, Class<out Any>>
    fun getUrl(): String
}