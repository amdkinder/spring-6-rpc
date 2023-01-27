package uz.anorbank.spring6rpc.web.rest

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange
import uz.anorbank.spring6rpc.service.JsonRpcHandler

@RestController
class JRpcController(
    private val rpcHandler: JsonRpcHandler<ServerWebExchange>
) {

}