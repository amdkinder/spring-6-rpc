package uz.anorbank.spring6rpc.web.rest

import jakarta.annotation.PostConstruct
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RestController
import uz.anorbank.spring6rpc.service.JsonRpcHandler
import uz.anorbank.spring6rpc.service.RpcProcessor

@RestController
class JRpcController(
    private val rpcProcessor: RpcProcessor,
    private val rpcHandler: JsonRpcHandler<HttpServletRequest>,

    ) {
    private val log = LoggerFactory.getLogger(this.javaClass)


    @PostConstruct
    fun init() {
        log.trace("init started")
        rpcHandler.registerEndpoints()
    }


}