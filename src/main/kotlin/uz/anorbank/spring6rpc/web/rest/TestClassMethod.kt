package uz.anorbank.spring6rpc.web.rest

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import uz.anorbank.spring6rpc.dummy.JRpcClassMethod

@Component
class TestClassMethod : JRpcClassMethod<String> {
    private val log = LoggerFactory.getLogger(this.javaClass)

    private fun sayHello(agentRequestId: String?, name: String?): String {
        log.info("Hello $agentRequestId, $name")
        return "Hello $agentRequestId, $name"
    }

    override fun invoke(params: Map<String, Any>): String {
        return sayHello(params["agentRequestId"] as String?, params["name"] as String?)
    }

    override fun getMethodName(): String {
        return "test.class.say"
    }

    override fun getParams(): Map<String, Class<out Any>> {
        return mapOf("agentRequestId" to String::class.java, "name" to String::class.java)
    }

    override fun getServiceUri(): String {
        return "/api/jsonrpc"
    }
}