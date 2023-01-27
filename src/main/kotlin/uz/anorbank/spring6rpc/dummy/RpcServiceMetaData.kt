package uz.anorbank.spring6rpc.dummy

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import uz.anorbank.spring6rpc.util.JsonRpcProcessorUtils

@Component
class RpcServiceMetaData(
     utils: JsonRpcProcessorUtils
) {
    private val log = LoggerFactory.getLogger(this.javaClass)
    private val rpcInfoList: List<JsonRpcServiceInfo?> = utils.getRpcInfoList()

    fun getRpcInfoList(): List<JsonRpcServiceInfo?> {
        log.trace("RpcServiceMetaData started")
        return rpcInfoList
    }
}