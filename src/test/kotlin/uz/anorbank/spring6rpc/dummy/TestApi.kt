package uz.anorbank.spring6rpc.dummy

import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import uz.anorbank.spring6rpc.annotation.*

@Service
@Validated
@JRpcService(JsonRpcDummyApiOne.URI)
class JsonRpcDummyApiOne {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @JRpcMethod(METHOD_DUMMY_OBJECT)
    fun dummyObject(
        @Valid @JRpcParam("requestOne") requestOne: DummyRequestOne,
        @Valid @JRpcParam("requestTwo") requestTwo: DummyRequestTwo
    ): DummyResponse {
        log.trace("dummyMethod started")
        return DummyResponse(requestOne.data + requestTwo.data, "SUCCESS")
    }

    @JRpcMethod(METHOD_DUMMY_OBJECT_CUSTOM_ERROR)
    @JRpcErrors(
        [JRpcError(
            exception = DummyActiveException::class,
            code = DUMMY_ERROR_CODE,
            message = DUMMY_MESSAGE,
            data = DUMMY_DATA
        ), JRpcError(
            exception = DummyInactiveException::class,
            code = DUMMY_ERROR_CODE2,
            message = DUMMY_MESSAGE2,
            data = DUMMY_DATA2
        )]
    )
    @Throws(
        DummyActiveException::class
    )
    fun dummyObjectCustomError(
        @Valid @JRpcParam("requestOne") requestOne: DummyRequestOne?,
        @Valid @JRpcParam("requestTwo") requestTwo: DummyRequestTwo?
    ): DummyResponse {
        log.trace("dummyObjectCustomError started")
        throw DummyActiveException()
    }

    @JRpcMethod(METHOD_DUMMY_LIST)
    fun dummy(
        @Valid @JRpcParam("requestOne") requestOne: DummyRequestOne,
        @Valid @JRpcParam("requestTwo") requestTwo: DummyRequestTwo
    ): ArrayList<DummyResponse> {
        log.trace("dummyMethod started")
        val result: ArrayList<DummyResponse> = ArrayList()
        val requestData = requestOne.data + requestTwo.data
        for (i in 0..4) {
            addElement(result, requestData, i)
        }
        return result
    }

    private fun addElement(result: ArrayList<DummyResponse>, requestData: String, order: Int) {
        log.trace("addElement started for order : {}", order)
        result.add(DummyResponse(requestData, order.toString()))
    }

    companion object {
        const val METHOD_DUMMY_OBJECT = "dummyMethodObject"
        const val METHOD_DUMMY_OBJECT_CUSTOM_ERROR = "dummyMethodObjectCustomError"
        const val METHOD_DUMMY_LIST = "dummyMethodList"
        const val URI = "/api/rpc/dummy/one"
        const val DUMMY_ERROR_CODE = 998
        const val DUMMY_ERROR_CODE2 = 999
        const val DUMMY_MESSAGE = "DUMMY_MESSAGE"
        const val DUMMY_MESSAGE2 = "DUMMY_MESSAGE2"
        const val DUMMY_DATA = "DUMMY_DATA"
        const val DUMMY_DATA2 = "DUMMY_DATA2"
    }
}


@Service
@Validated
@JRpcService(JsonRpcDummyApiTwo.URI)
class JsonRpcDummyApiTwo {

    private val log = LoggerFactory.getLogger(this.javaClass)

    @JRpcMethod(METHOD_DUMMY_Object)
    fun dummyObject(@Valid @JRpcParam("requestOne") requestOne: DummyRequestOne): DummyResponse {
        log.trace("dummyMethod started")
        return DummyResponse(requestOne.data, "SUCCESS");
    }

    @JRpcMethod(METHOD_DUMMY_LIST)
    fun dummyList(@Valid @JRpcParam("requestOne") requestOne: DummyRequestOne): ArrayList<DummyResponse> {
        log.trace("dummyMethod started")
        val result = java.util.ArrayList<DummyResponse>()
        val requestData = requestOne.data
        for (i in 0..1) {
            addElement(result, requestData!!, i)
        }
        return result
    }

    private fun addElement(result: ArrayList<DummyResponse>, requestData: String, order: Int) {
        log.trace("addElement started for order : {}", order)
        result.add(DummyResponse(requestData, order.toString()))
    }

    companion object {
        const val METHOD_DUMMY_Object = "dummyMethodObject"
        const val METHOD_DUMMY_LIST = "dummyMethodList"
        const val URI = "/api/rpc/dummy/two"
    }
}

@Validated
@JRpcService("/api/rpc/dummy/three")
interface JsonRpcDummyApiThree {
    @JRpcMethod("dummyMethodObject")
    fun dummyObject(
        @Valid @JRpcParam("requestOne") requestOne: DummyRequestOne?,
        @Valid @JRpcParam("requestTwo") requestTwo: DummyRequestTwo?
    ): DummyResponse
}

@Service
class JsonRpcDummyApiThreeImpl : JsonRpcDummyApiThree {
    private val log = LoggerFactory.getLogger(this.javaClass)

    override fun dummyObject(requestOne: DummyRequestOne?, requestTwo: DummyRequestTwo?): DummyResponse {
        log.trace("dummyMethod started")
        return DummyResponse(requestOne!!.data + requestTwo!!.data, "SUCCESS")
    }
}
