package uz.anorbank.spring6rpc.annotation

import java.lang.annotation.*

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class JRpcErrors(
    val value: Array<JRpcError>
)
