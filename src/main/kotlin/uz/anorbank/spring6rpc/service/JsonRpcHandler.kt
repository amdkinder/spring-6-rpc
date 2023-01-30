package uz.anorbank.spring6rpc.service

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.server.ServerWebExchange
import uz.anorbank.spring6rpc.dummy.JsonRpcRequest

interface JsonRpcHandler<TRequestDetails> {
    /**
     *
     */
    fun registerEndpoints()

    /**
     *
     */
    fun handle(@Valid @RequestBody @NotNull request: JsonRpcRequest, servletRequest: HttpServletRequest): ResponseEntity<*>


}