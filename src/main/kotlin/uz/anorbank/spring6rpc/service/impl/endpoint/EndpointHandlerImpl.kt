package uz.anorbank.spring6rpc.service.impl.endpoint

import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import uz.anorbank.spring6rpc.dummy.JsonRpcRequest
import uz.anorbank.spring6rpc.dummy.RpcServiceMetaData
import uz.anorbank.spring6rpc.service.JsonRpcHandler
import uz.anorbank.spring6rpc.service.RpcProcessor
import java.lang.reflect.Method

@Component
class EndpointHandlerImpl(
    @Qualifier("requestMappingHandlerMapping")
    private val handlerMapping: RequestMappingHandlerMapping,
    private val metaData: RpcServiceMetaData,
    private val processor: RpcProcessor,
) : JsonRpcHandler<ServerWebExchange> {
    private val log = LoggerFactory.getLogger(this.javaClass)
    override fun registerEndpoints() {
        handlerMapping.registerMapping(
            RequestMappingInfo
                .paths(*metaData.getRpcInfoList().map { it!!.uri!! }.toTypedArray())
                .methods(RequestMethod.POST)
                .produces(MediaType.APPLICATION_NDJSON_VALUE)
                .build(),
            this,
            getEndpoint()
        )
    }

    private fun getEndpoint(): Method {
        log.trace("getEndpoint started")
        return this.javaClass.getDeclaredMethod("handle", JsonRpcRequest::class.java, ServerWebExchange::class.java)
    }

    override fun handle(
        @Valid @RequestBody @NotNull request: JsonRpcRequest,
        exchange: ServerWebExchange
    ): ResponseEntity<*> {
        log.debug(
            "RPC request to common rpc endpoint. uri : {}, body : {}",
            exchange.request.path.value(),
            request
        )
        exchange.attributes["request"] = request
        return processor.process(request, exchange.request.path.value())!!
    }
}