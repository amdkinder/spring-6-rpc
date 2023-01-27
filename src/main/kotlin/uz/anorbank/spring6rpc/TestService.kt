package uz.anorbank.spring6rpc

import org.springframework.stereotype.Service
import uz.anorbank.spring6rpc.annotation.*

@Service
@JRpcService("/api")
class TestService {


    @JRpcErrors([
            JRpcError(code = 0, exception = RuntimeException::class)
    ])
    @JRpcMethod("method")
    fun testRpc(@JRpcParam("attr") attr: String) {

    }
}