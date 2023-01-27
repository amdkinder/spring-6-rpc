package uz.anorbank.spring6rpc.dummy

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class JsonRpcResponse(

    @get:NotNull
    @JsonProperty("id")
    var id: String? = null,

    @get:NotBlank
    @JsonProperty("jsonrpc")
    var jsonrpc: String? = null,

    @JsonProperty("result")
    var result: Any? = null,

    @JsonProperty("error")
    var error: JsonRpcError? = null
): JResponse