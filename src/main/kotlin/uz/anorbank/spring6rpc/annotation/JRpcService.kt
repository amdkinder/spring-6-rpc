package uz.anorbank.spring6rpc.annotation

import org.springframework.stereotype.Component
import java.lang.annotation.*

@Component
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class JRpcService(
    val value: String
)
