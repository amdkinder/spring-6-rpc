package uz.anorbank.spring6rpc.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RpcConfiguration {

    companion object {
        const val OBJECT_MAPPER_BEAN_NAME = "jsonMapperObjectMapper"
    }

    @Bean(OBJECT_MAPPER_BEAN_NAME)
    fun objectMapper(): ObjectMapper? {
        val objectMapper = ObjectMapper()
//        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false)
        return objectMapper
    }

}