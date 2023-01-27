package uz.anorbank.spring6rpc.dummy

import jakarta.validation.constraints.NotNull

data class DummyRequestOne(
    var data: String? = null
)

data class DummyRequestTwo(
    var data: String? = null
)

data class ApiOneRequest(
    @get:NotNull
    var requestOne: DummyRequestOne? = null,
    @get:NotNull
    var requestTwo: DummyRequestTwo? = null
)

data class ApiTwoRequest(
    @get:NotNull
    var requestOne: DummyRequestOne? = null,
)

data class DummyResponse(
    var requestData: String? = null,
    var result: String? = null
)
