package uz.anorbank.spring6rpc.annotation

import java.lang.annotation.*

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class JRpcParam(
    val value: String
)
