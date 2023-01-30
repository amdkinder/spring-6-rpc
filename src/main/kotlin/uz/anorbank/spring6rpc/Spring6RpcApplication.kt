package uz.anorbank.spring6rpc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import uz.anorbank.spring6rpc.config.JsonRpcProperties

@SpringBootApplication
@EnableConfigurationProperties(JsonRpcProperties::class)
class Spring6RpcApplication

fun main(args: Array<String>) {
    runApplication<Spring6RpcApplication>(*args)
}
