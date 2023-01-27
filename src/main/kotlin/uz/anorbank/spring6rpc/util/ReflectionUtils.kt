package uz.anorbank.spring6rpc.util

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.lang.reflect.InvocationTargetException

@Component
class ReflectionUtils {

    private val log = LoggerFactory.getLogger(this.javaClass)

    fun extractError(e: Exception): Exception {
        log.trace("extractError started")
        return if (e.javaClass == InvocationTargetException::class.java) getTargetException(e) else e
    }

    private fun getTargetException(e: Exception): Exception {
        log.trace("getTargetException started")
        val ex = (e as InvocationTargetException).targetException
        return ex as Exception? ?: e
    }
}