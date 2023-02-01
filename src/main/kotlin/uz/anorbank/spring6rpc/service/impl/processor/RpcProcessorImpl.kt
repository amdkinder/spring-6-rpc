package uz.anorbank.spring6rpc.service.impl.processor

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import uz.anorbank.spring6rpc.dummy.JsonRpcRequest
import uz.anorbank.spring6rpc.dummy.JsonRpcResponse
import uz.anorbank.spring6rpc.dummy.RpcServiceMetaData
import uz.anorbank.spring6rpc.enumuration.ErrorEnum
import uz.anorbank.spring6rpc.exception.MethodNotFoundException
import uz.anorbank.spring6rpc.service.ErrorHandler
import uz.anorbank.spring6rpc.service.RpcExecutor
import uz.anorbank.spring6rpc.service.RpcProcessor
import uz.anorbank.spring6rpc.util.JsonRpcProcessorUtils

@Component
class RpcProcessorImpl(
    private val metaData: RpcServiceMetaData,
    private val utils: JsonRpcProcessorUtils,
    private val executorList: List<RpcExecutor<JsonRpcRequest, JsonRpcResponse>>

) : RpcProcessor {
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun process(request: JsonRpcRequest?, uri: String?): ResponseEntity<*>? {
        log.debug("endpoint started for request : {}", request)
        try {

            utils.validateRequest(request!!)

            val jsonRpcInfo = metaData
                .getRpcInfoList()
                .find { rpcInfo -> rpcInfo!!.uri.equals(uri, true) && utils.isDesiredMethod(request, rpcInfo) }
                ?: throw MethodNotFoundException()
            var reqNext = request

            executorList.forEach { reqNext = it.before(reqNext!!) }
            var rpcResponse = utils.executeMethod(request, jsonRpcInfo)
            executorList.forEach { rpcResponse = it.after(rpcResponse as JsonRpcResponse) }

            val result = utils.mapResult(request, jsonRpcInfo, rpcResponse)
            log.debug("endpoint successfully ended for request : {}", request)
            return result

        } catch (e: Exception) {
            log.debug("Endpoint throws exception: ${e.message}")

            val errorExp: ErrorHandler =
                metaData
                    .getAllErrorHandlers()
                    .find { e.javaClass == it.getExceptionType()!!.java }
                    ?: ErrorEnum.COMMON
            return ResponseEntity.ok(errorExp.buildResponse(e, request))
        }

    }
}