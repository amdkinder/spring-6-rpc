package uz.anorbank.spring6rpc.dummy

import jakarta.validation.constraints.NotNull

data class JsonRpcError(
    @get:NotNull
    var code: Int? = null,
    @get:NotNull
    var message: String? = null,
    @get:NotNull
    var data: String? = null,
) : JResponse