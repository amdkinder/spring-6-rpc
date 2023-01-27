package uz.anorbank.spring6rpc

import org.springframework.boot.test.context.SpringBootTest

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest(classes = [TestConfiguration::class])
annotation class IntegrationTest()
