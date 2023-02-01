package uz.anorbank.spring6rpc.service

import org.springframework.stereotype.Component
import uz.anorbank.spring6rpc.dummy.JsonRpcRequest
import uz.anorbank.spring6rpc.dummy.JsonRpcResponse

@Component
class DefaultExecutor: RpcExecutor<JsonRpcRequest, JsonRpcResponse> {
    override fun before(data: JsonRpcRequest): JsonRpcRequest {
        return data
    }

    override fun after(data: JsonRpcResponse): JsonRpcResponse {
        return data
    }
}