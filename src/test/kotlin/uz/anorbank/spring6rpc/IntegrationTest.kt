package uz.anorbank.spring6rpc

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@AutoConfigureMockMvc
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest(classes = [TestConfiguration::class])
annotation class IntegrationTest()
