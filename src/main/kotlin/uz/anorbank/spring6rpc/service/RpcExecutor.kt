package uz.anorbank.spring6rpc.service

interface RpcExecutor<RPC_REQ, RPC_RES> {
    fun before(data: RPC_REQ): RPC_REQ
    fun after(data: RPC_RES): RPC_RES
}

