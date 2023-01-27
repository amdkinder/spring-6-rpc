package uz.anorbank.spring6rpc.service.impl.processor

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import uz.anorbank.spring6rpc.exception.MethodNotFoundException
import uz.anorbank.spring6rpc.dummy.JsonRpcRequest
import uz.anorbank.spring6rpc.dummy.RpcServiceMetaData
import uz.anorbank.spring6rpc.service.RpcProcessor
import uz.anorbank.spring6rpc.util.JsonRpcProcessorUtils

@Component
class RpcProcessorImpl(
    private val metaData: RpcServiceMetaData,
    private val utils: JsonRpcProcessorUtils
) : RpcProcessor {
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun process(request: JsonRpcRequest?, uri: String?): ResponseEntity<*>? {
        log.debug("endpoint started for request : {}", request)

        utils.validateRequest(request!!)

        val jsonRpcInfo = metaData
            .getRpcInfoList()
            .find { rpcInfo -> rpcInfo!!.uri.equals(uri, true) && utils.isDesiredMethod(request, rpcInfo) }
            ?: throw MethodNotFoundException()

        val result = utils.mapResult(request, jsonRpcInfo, utils.executeMethod(request, jsonRpcInfo))

        log.debug("endpoint successfully ended for request : {}", request)

        return result
    }
}