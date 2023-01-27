package uz.anorbank.spring6rpc.dummy

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class JsonRpcRequest(
    @get:NotNull
    @JsonProperty("id")
    var id: String? = null,

    @get:NotBlank
    @JsonProperty("jsonrpc")
    var jsonrpc: String? = null,

    @get:NotBlank
    @JsonProperty("method")
    var method: String? = null,

    @get:NotNull
    @JsonProperty("params")
    var params: Any? = null
)