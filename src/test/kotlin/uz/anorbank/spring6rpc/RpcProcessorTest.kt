package uz.anorbank.spring6rpc

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.SerializationFeature
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import uz.anorbank.spring6rpc.dummy.*


@IntegrationTest
internal class JRpcProcessorTest {

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    @Qualifier(TestConfiguration.TEST_WRITER)
    lateinit var objectWriter: ObjectWriter

    @Autowired
    lateinit var restMockMvc: MockMvc


    private val log = LoggerFactory.getLogger(this.javaClass)

    @Test
    fun monoMethodSuccessApiOneTest() {
        log.debug("monoMethodSuccessApiOneTest started")
        val param = ApiOneRequest(DummyRequestOne(OBJECT_DATA_1), DummyRequestTwo(OBJECT_DATA_2))
        val request = JsonRpcRequest(OBJECT_REQUEST_ID, JSONRPC, JsonRpcDummyApiOne.METHOD_DUMMY_OBJECT, param)
        objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);

        restMockMvc.perform(
            post(URI_API_1)
                .accept(MEDIA_TYPE)
                .content(objectWriter.writeValueAsString(request))
        ).andExpect(status().isBadGateway)


//        assertBaseParams(result, OBJECT_REQUEST_ID)
//        val resultParam: Unit = objectMapper.convertValue(result.getResult(), DummyResponse::class.java)
//        Assertions.assertEquals(
//            param.getRequestOne().getData() + param.getRequestTwo().getData(),
//            resultParam.getRequestData()
//        )
//        Assertions.assertEquals("SUCCESS", resultParam.getResult())
    }
//
//    @Test
//    fun monoMethodSuccessApiOneGatlingTest() {
//        log.debug("monoMethodSuccessApiOneGatlingTest started")
//        getRequests(false, true).parallelStream().forEach(Consumer<JsonRpcRequest> { request: JsonRpcRequest ->
//            val result: Unit = webTestClient
//                .post()
//                .uri(URI_API_1)
//                .accept(MEDIA_TYPE)
//                .bodyValue(request)
//                .exchange()
//                .expectStatus()
//                .isOk
//                .expectHeader()
//                .contentType(MEDIA_TYPE)
//                .returnResult(JsonRpcResponse::class.java)
//                .getResponseBody()
//                .next()
//                .block()
//            Assertions.assertNotNull(result)
//            assertBaseParams(result, request.getId())
//            val resultParam: Unit = objectMapper.convertValue(result.getResult(), DummyResponse::class.java)
//            Assertions.assertEquals("SUCCESS", resultParam.getResult())
//        })
//    }
//
//    @Test
//    fun fluxMethodSuccessApiOneTest() {
//        log.debug("fluxMethodTest started")
//        val param = ApiOneRequest(DummyRequestOne(LIST_DATA_1), DummyRequestTwo(LIST_DATA_2))
//        val request = JsonRpcRequest(LIST_REQUEST_ID, JSONRPC, JsonRpcDummyApiOne.METHOD_DUMMY_FLUX, param)
//        val result: Unit = webTestClient
//            .post()
//            .uri(URI_API_1)
//            .accept(MEDIA_TYPE)
//            .bodyValue(request)
//            .exchange()
//            .expectStatus()
//            .isOk
//            .expectHeader()
//            .contentType(MEDIA_TYPE)
//            .returnResult(JsonRpcResponse::class.java)
//            .getResponseBody()
//        StepVerifier
//            .create(result)
//            .expectNextMatches { jsonRpcResponse ->
//                assertBaseParams(jsonRpcResponse, LIST_REQUEST_ID)
//                val response: Unit = objectMapper.convertValue(jsonRpcResponse.getResult(), DummyResponse::class.java)
//                response.getResult().equals("0")
//            }
//            .expectNextMatches { jsonRpcResponse ->
//                assertBaseParams(jsonRpcResponse, LIST_REQUEST_ID)
//                val response: Unit = objectMapper.convertValue(jsonRpcResponse.getResult(), DummyResponse::class.java)
//                response.getResult().equals("1")
//            }
//            .expectNextMatches { jsonRpcResponse ->
//                assertBaseParams(jsonRpcResponse, LIST_REQUEST_ID)
//                val response: Unit = objectMapper.convertValue(jsonRpcResponse.getResult(), DummyResponse::class.java)
//                response.getResult().equals("2")
//            }
//            .expectNextMatches { jsonRpcResponse ->
//                assertBaseParams(jsonRpcResponse, LIST_REQUEST_ID)
//                val response: Unit = objectMapper.convertValue(jsonRpcResponse.getResult(), DummyResponse::class.java)
//                response.getResult().equals("3")
//            }
//            .expectNextMatches { jsonRpcResponse ->
//                assertBaseParams(jsonRpcResponse, LIST_REQUEST_ID)
//                val response: Unit = objectMapper.convertValue(jsonRpcResponse.getResult(), DummyResponse::class.java)
//                response.getResult().equals("4")
//            }
//            .verifyComplete()
//    }
//
//    @Test
//    fun fluxMethodSuccessApiOneGatlingTest() {
//        log.debug("fluxMethodSuccessApiOneGatlingTest started")
//        getRequests(false, false).parallelStream().forEach(Consumer<JsonRpcRequest> { request: JsonRpcRequest ->
//            val result: Unit = webTestClient
//                .post()
//                .uri(URI_API_1)
//                .accept(MEDIA_TYPE)
//                .bodyValue(request)
//                .exchange()
//                .expectStatus()
//                .isOk
//                .expectHeader()
//                .contentType(MEDIA_TYPE)
//                .returnResult(JsonRpcResponse::class.java)
//                .getResponseBody()
//            StepVerifier
//                .create(result)
//                .expectNextMatches { jsonRpcResponse ->
//                    assertBaseParams(jsonRpcResponse, request.getId())
//                    val response: Unit =
//                        objectMapper.convertValue(jsonRpcResponse.getResult(), DummyResponse::class.java)
//                    response.getResult().equals("0")
//                }
//                .expectNextMatches { jsonRpcResponse ->
//                    assertBaseParams(jsonRpcResponse, request.getId())
//                    val response: Unit =
//                        objectMapper.convertValue(jsonRpcResponse.getResult(), DummyResponse::class.java)
//                    response.getResult().equals("1")
//                }
//                .expectNextMatches { jsonRpcResponse ->
//                    assertBaseParams(jsonRpcResponse, request.getId())
//                    val response: Unit =
//                        objectMapper.convertValue(jsonRpcResponse.getResult(), DummyResponse::class.java)
//                    response.getResult().equals("2")
//                }
//                .expectNextMatches { jsonRpcResponse ->
//                    assertBaseParams(jsonRpcResponse, request.getId())
//                    val response: Unit =
//                        objectMapper.convertValue(jsonRpcResponse.getResult(), DummyResponse::class.java)
//                    response.getResult().equals("3")
//                }
//                .expectNextMatches { jsonRpcResponse ->
//                    assertBaseParams(jsonRpcResponse, request.getId())
//                    val response: Unit =
//                        objectMapper.convertValue(jsonRpcResponse.getResult(), DummyResponse::class.java)
//                    response.getResult().equals("4")
//                }
//                .verifyComplete()
//        })
//    }
//
//    @Test
//    fun methodNotFoundErrorApiOneTest() {
//        log.debug("methodNotFoundErrorTest started")
//        val param = ApiOneRequest(DummyRequestOne(OBJECT_DATA_1), DummyRequestTwo(OBJECT_DATA_2))
//        val request = JsonRpcRequest(OBJECT_REQUEST_ID, JSONRPC, "NONE_METHOD", param)
//        val result: Unit = webTestClient
//            .post()
//            .uri(URI_API_1)
//            .accept(MEDIA_TYPE)
//            .bodyValue(request)
//            .exchange()
//            .expectStatus()
//            .isOk
//            .expectHeader()
//            .contentType(MEDIA_TYPE)
//            .returnResult(JsonRpcResponse::class.java)
//            .getResponseBody()
//            .next()
//            .block()
//        Assertions.assertNotNull(result)
//        assertBaseParams(result, OBJECT_REQUEST_ID)
//        val error: Unit = objectMapper.convertValue(result.getError(), JsonRpcError::class.java)
//        Assertions.assertEquals(MethodNotFoundException::class.java.getSimpleName(), error.getData())
//        Assertions.assertNotNull(error.getMessage())
//        Assertions.assertEquals(-32601, error.getCode())
//    }
//
//    @Test
//    fun invalidParamsErrorApiOneTest() {
//        log.debug("invalidParamsErrorTest started")
//        val param = DummyRequestOne(OBJECT_DATA_1)
//        val request = JsonRpcRequest(OBJECT_REQUEST_ID, JSONRPC, JsonRpcDummyApiOne.METHOD_DUMMY_MONO, param)
//        val result: Unit = webTestClient
//            .post()
//            .uri(URI_API_1)
//            .accept(MEDIA_TYPE)
//            .bodyValue(request)
//            .exchange()
//            .expectStatus()
//            .isOk
//            .expectHeader()
//            .contentType(MEDIA_TYPE)
//            .returnResult(JsonRpcResponse::class.java)
//            .getResponseBody()
//            .next()
//            .block()
//        Assertions.assertNotNull(result)
//        assertBaseParams(result, OBJECT_REQUEST_ID)
//        val error: Unit = objectMapper.convertValue(result.getError(), JsonRpcError::class.java)
//        Assertions.assertEquals(InvalidParamsException::class.java.getSimpleName(), error.getData())
//        Assertions.assertNotNull(error.getMessage())
//        Assertions.assertEquals(-32602, error.getCode())
//    }
//
//    @Test
//    fun argsValidationErrorApiOneTest() {
//        log.debug("argsValidationErrorTest started")
//        val param = ApiOneRequest(DummyRequestOne(null), DummyRequestTwo(OBJECT_DATA_2))
//        val request = JsonRpcRequest(OBJECT_REQUEST_ID, JSONRPC, JsonRpcDummyApiOne.METHOD_DUMMY_MONO, param)
//        val result: Unit = webTestClient
//            .post()
//            .uri(URI_API_1)
//            .accept(MEDIA_TYPE)
//            .bodyValue(request)
//            .exchange()
//            .expectStatus()
//            .isOk
//            .expectHeader()
//            .contentType(MEDIA_TYPE)
//            .returnResult(JsonRpcResponse::class.java)
//            .getResponseBody()
//            .next()
//            .block()
//        Assertions.assertNotNull(result)
//        assertBaseParams(result, OBJECT_REQUEST_ID)
//        val error: Unit = objectMapper.convertValue(result.getError(), JsonRpcError::class.java)
//        Assertions.assertEquals(ConstraintViolationException::class.java.getSimpleName(), error.getData())
//        Assertions.assertNotNull(error.getMessage())
//        Assertions.assertEquals(-32600, error.getCode())
//    }
//
//    @Test
//    fun argsValidationErrorApiOneGatlingTest() {
//        log.debug("argsValidationErrorApiOneGatlingTest started")
//        getRequests(true, true).parallelStream().forEach(Consumer<JsonRpcRequest> { request: JsonRpcRequest ->
//            val result: Unit = webTestClient
//                .post()
//                .uri(URI_API_1)
//                .accept(MEDIA_TYPE)
//                .bodyValue(request)
//                .exchange()
//                .expectStatus()
//                .isOk
//                .expectHeader()
//                .contentType(MEDIA_TYPE)
//                .returnResult(JsonRpcResponse::class.java)
//                .getResponseBody()
//                .next()
//                .block()
//            Assertions.assertNotNull(result)
//            assertBaseParams(result, request.getId())
//            val error: Unit = objectMapper.convertValue(result.getError(), JsonRpcError::class.java)
//            Assertions.assertEquals(
//                ConstraintViolationException::class.java.getSimpleName(),
//                error.getData()
//            )
//            Assertions.assertNotNull(error.getMessage())
//            Assertions.assertEquals(-32600, error.getCode())
//        })
//    }
//
//    @Test
//    fun userDefinedErrorApiOneTest() {
//        log.debug("userDefinedErrorApiOneTest started")
//        val param = ApiOneRequest(DummyRequestOne(OBJECT_DATA_1), DummyRequestTwo(OBJECT_DATA_2))
//        val request =
//            JsonRpcRequest(OBJECT_REQUEST_ID, JSONRPC, JsonRpcDummyApiOne.METHOD_DUMMY_MONO_CUSTOM_ERROR, param)
//        val result: Unit = webTestClient
//            .post()
//            .uri(URI_API_1)
//            .accept(MEDIA_TYPE)
//            .bodyValue(request)
//            .exchange()
//            .expectStatus()
//            .isOk
//            .expectHeader()
//            .contentType(MEDIA_TYPE)
//            .returnResult(JsonRpcResponse::class.java)
//            .getResponseBody()
//            .next()
//            .block()
//        Assertions.assertNotNull(result)
//        assertBaseParams(result, OBJECT_REQUEST_ID)
//        val error: Unit = objectMapper.convertValue(result.getError(), JsonRpcError::class.java)
//        Assertions.assertEquals(JsonRpcDummyApiOne.DUMMY_DATA, error.getData())
//        Assertions.assertEquals(JsonRpcDummyApiOne.DUMMY_MESSAGE, error.getMessage())
//        Assertions.assertEquals(JsonRpcDummyApiOne.DUMMY_ERROR_CODE, error.getCode())
//    }
//
//    @Test
//    fun versionValidationErrorApiOneTest() {
//        log.debug("versionValidationErrorApiOneTest started")
//        val param = ApiOneRequest(DummyRequestOne(null), DummyRequestTwo(OBJECT_DATA_2))
//        val request = JsonRpcRequest(OBJECT_REQUEST_ID, WRONG_RPC_VERSION, JsonRpcDummyApiOne.METHOD_DUMMY_MONO, param)
//        val result: Unit = webTestClient
//            .post()
//            .uri(URI_API_1)
//            .accept(MEDIA_TYPE)
//            .bodyValue(request)
//            .exchange()
//            .expectStatus()
//            .isOk
//            .expectHeader()
//            .contentType(MEDIA_TYPE)
//            .returnResult(JsonRpcResponse::class.java)
//            .getResponseBody()
//            .next()
//            .block()
//        Assertions.assertNotNull(result)
//        Assertions.assertEquals(OBJECT_REQUEST_ID, result.getId())
//        val error: Unit = objectMapper.convertValue(result.getError(), JsonRpcError::class.java)
//        Assertions.assertEquals(JsonRpcVersionValidationException::class.java.getSimpleName(), error.getData())
//        Assertions.assertNotNull(error.getMessage())
//        Assertions.assertEquals(-32600, error.getCode())
//    }
//
//    @Test
//    fun monoMethodSuccessApiTwoTest() {
//        log.debug("monoMethodTest started")
//        val param = ApiTwoRequest(DummyRequestOne(LIST_DATA_1))
//        val request = JsonRpcRequest(OBJECT_REQUEST_ID, JSONRPC, JsonRpcDummyApiTwo.METHOD_DUMMY_MONO, param)
//        val result: Unit = webTestClient
//            .post()
//            .uri(URI_API_2)
//            .accept(MEDIA_TYPE)
//            .bodyValue(request)
//            .exchange()
//            .expectStatus()
//            .isOk
//            .expectHeader()
//            .contentType(MEDIA_TYPE)
//            .returnResult(JsonRpcResponse::class.java)
//            .getResponseBody()
//            .next()
//            .block()
//        Assertions.assertNotNull(result)
//        assertBaseParams(result, OBJECT_REQUEST_ID)
//        val resultParam: Unit = objectMapper.convertValue(result.getResult(), DummyResponse::class.java)
//        Assertions.assertEquals(param.getRequestOne().getData(), resultParam.getRequestData())
//        Assertions.assertEquals("SUCCESS", resultParam.getResult())
//    }
//
//    @Test
//    fun fluxMethodSuccessApiTwoTest() {
//        log.debug("fluxMethodTest started")
//        val param = ApiTwoRequest(DummyRequestOne(LIST_DATA_1))
//        val request = JsonRpcRequest(LIST_REQUEST_ID, JSONRPC, JsonRpcDummyApiTwo.METHOD_DUMMY_FLUX, param)
//        val result: Unit = webTestClient
//            .post()
//            .uri(URI_API_2)
//            .accept(MEDIA_TYPE)
//            .bodyValue(request)
//            .exchange()
//            .expectStatus()
//            .isOk
//            .expectHeader()
//            .contentType(MEDIA_TYPE)
//            .returnResult(JsonRpcResponse::class.java)
//            .getResponseBody()
//        StepVerifier
//            .create(result)
//            .expectNextMatches { jsonRpcResponse ->
//                assertBaseParams(jsonRpcResponse, LIST_REQUEST_ID)
//                val response: Unit = objectMapper.convertValue(jsonRpcResponse.getResult(), DummyResponse::class.java)
//                response.getResult().equals("0")
//            }
//            .expectNextMatches { jsonRpcResponse ->
//                assertBaseParams(jsonRpcResponse, LIST_REQUEST_ID)
//                val response: Unit = objectMapper.convertValue(jsonRpcResponse.getResult(), DummyResponse::class.java)
//                response.getResult().equals("1")
//            }
//            .verifyComplete()
//    }
//
//    @Test
//    fun monoMethodSuccessApiThreeTest() {
//        log.debug("monoMethodSuccessApiThreeTest started")
//        val param = ApiOneRequest(DummyRequestOne(OBJECT_DATA_1), DummyRequestTwo(OBJECT_DATA_2))
//        val request = JsonRpcRequest(OBJECT_REQUEST_ID, JSONRPC, JsonRpcDummyApiOne.METHOD_DUMMY_MONO, param)
//        val result = webTestClient
//            .post()
//            .uri(URI_API_3)
//            .accept(MEDIA_TYPE)
//            .bodyValue(request)
//            .exchange()
//            .expectStatus()
//            .isOk
//            .expectHeader()
//            .contentType(MEDIA_TYPE)
//            .returnResult(JsonRpcResponse::class.java)
//            .getResponseBody()
//            .next()
//            .block()
//        Assertions.assertNotNull(result)
//        assertBaseParams(result, OBJECT_REQUEST_ID)
//        val resultParam = objectMapper.convertValue(result.getResult(), DummyResponse::class.java)
//        Assertions.assertEquals(
//            param.getRequestOne().getData() + param.getRequestTwo().getData(),
//            resultParam.getRequestData()
//        )
//        Assertions.assertEquals("SUCCESS", resultParam.getResult())
//    }

    private fun assertBaseParams(response: JsonRpcResponse, requestId: Long) {
        Assertions.assertEquals(requestId, response.id)
        Assertions.assertEquals(JSONRPC, response.result)
    }

    private fun getRequests(isValidationCase: Boolean, isMonoCase: Boolean): List<JsonRpcRequest> {
        log.trace("getRequests started")
        val requests: ArrayList<JsonRpcRequest> = ArrayList<JsonRpcRequest>()
        for (i in 0 until GATLING_REQUESTS_COUNT) {
            val param = ApiOneRequest(
                DummyRequestOne(if (isValidationCase) null else i.toString()),
                DummyRequestTwo((i + 1).toString())
            )
            requests.add(
                JsonRpcRequest(
                    "$i",
                    JSONRPC,
                    if (isMonoCase) JsonRpcDummyApiOne.METHOD_DUMMY_OBJECT else JsonRpcDummyApiOne.METHOD_DUMMY_LIST,
                    param
                )
            )
        }
        return requests
    }

    companion object {
        val MEDIA_TYPE = MediaType.APPLICATION_JSON
        const val JSONRPC = "2.0"
        const val OBJECT_REQUEST_ID = "1"
        const val LIST_REQUEST_ID = "2"
        const val LIST_DATA_1 = "FLUX1"
        const val LIST_DATA_2 = "FLUX2"
        const val OBJECT_DATA_1 = "1"
        const val OBJECT_DATA_2 = "2"
        const val URI_API_1 = JsonRpcDummyApiOne.URI
        const val URI_API_2 = JsonRpcDummyApiTwo.URI
        const val WRONG_RPC_VERSION = "1"
        const val URI_API_3 = "/api/rpc/dummy/three"
        const val GATLING_REQUESTS_COUNT = 50
    }
}
