package uz.anorbank.spring6rpc.service

import jakarta.validation.constraints.NotNull
import org.springframework.http.ResponseEntity
import uz.anorbank.spring6rpc.dummy.JsonRpcRequest

interface RpcProcessor {
    fun process(@NotNull request: JsonRpcRequest?, uri: String?): ResponseEntity<*>?
}
