package uz.anorbank.spring6rpc.dummy

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import uz.anorbank.spring6rpc.service.ErrorHandler
import uz.anorbank.spring6rpc.util.JsonRpcProcessorUtils

@Component
class RpcServiceMetaData(
    utils: JsonRpcProcessorUtils
) {
    private val log = LoggerFactory.getLogger(this.javaClass)
    private val rpcInfoList: MutableList<JsonRpcServiceInfo?> = utils.getRpcInfoList().toMutableList().apply { addAll(utils.getJsonRpcInfoClassMethods()) }
    private val errorHandlers: List<ErrorHandler> = utils.getAllErrorHandlers().toList()

    fun getRpcInfoList(): List<JsonRpcServiceInfo?> {
        log.trace("RpcServiceMetaData started")
        return rpcInfoList
    }

    fun getAllErrorHandlers(): List<ErrorHandler> {
        return errorHandlers
    }
}