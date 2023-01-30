package uz.anorbank.spring6rpc.service.impl.endpoint

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import org.springframework.web.util.pattern.PathPatternParser
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
) : JsonRpcHandler<HttpServletRequest> {

    private val log = LoggerFactory.getLogger(this.javaClass)

    private fun builderOptions() {
        val options = RequestMappingInfo.BuilderConfiguration()
        options.patternParser = PathPatternParser()
    }

    /**
     *
     */
    override fun registerEndpoints() {
        handlerMapping.registerMapping(
            RequestMappingInfo
                .paths(*metaData.getRpcInfoList().map { it!!.uri!! }.toTypedArray())
                .methods(RequestMethod.POST)
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .options(RequestMappingInfo
                    .BuilderConfiguration()
                    .apply { patternParser = PathPatternParser() })
                .build(),
            this,
            getEndpoint()
        )
    }


    /**
     *
     */
    private fun getEndpoint(): Method {
        log.trace("getEndpoint started")
        return this::class.java.getDeclaredMethod("handle", JsonRpcRequest::class.java, HttpServletRequest::class.java)
    }

    /**
     *
     */
    override fun handle(
        @Valid @RequestBody @NotNull request: JsonRpcRequest,
        servletRequest: HttpServletRequest
    ): ResponseEntity<*> {
        log.debug(
            "RPC request to common rpc endpoint. uri : {}, body : {}",
            servletRequest.requestURI,
            request
        )
//        exchange.c["request"] = request
        return processor.process(request, servletRequest.requestURI)!!
    }
}