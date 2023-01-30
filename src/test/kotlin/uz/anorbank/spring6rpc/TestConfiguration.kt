package uz.anorbank.spring6rpc

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan

@TestConfiguration
@ComponentScan(basePackages = ["uz.anorbank"])
class TestConfiguration {
    companion object {
        const val TEST_WRITER = "testObjectWriter"

    }
    @Bean(TEST_WRITER)
    fun objectWriter(): ObjectWriter {
        val writer = ObjectMapper().apply {
            configure(SerializationFeature.WRAP_ROOT_VALUE, false)
        }.writer().withDefaultPrettyPrinter()
        return writer
    }
}