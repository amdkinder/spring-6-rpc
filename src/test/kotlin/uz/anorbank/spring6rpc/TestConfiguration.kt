package uz.anorbank.spring6rpc

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.web.reactive.server.WebTestClient

@TestConfiguration
@ComponentScan(basePackages = ["uz.anorbank"])
class TestConfiguration {


    @Bean
    fun webTestClient(): WebTestClient {
        return DefaultWebTest-
    }
}