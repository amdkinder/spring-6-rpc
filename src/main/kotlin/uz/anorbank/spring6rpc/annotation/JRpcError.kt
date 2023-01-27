package uz.anorbank.spring6rpc.annotation

import java.lang.annotation.*
import kotlin.reflect.KClass

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE)
annotation class JRpcError(
    val code: Int = 0,
    val message: String = "",
    val data: String = "",
    val exception: KClass<out Throwable>

)
