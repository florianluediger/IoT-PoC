package iot.poc.sensor.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import java.net.http.HttpClient

@Configuration
@EnableScheduling
class SensorConfig {
    @Bean
    fun httpClient(): HttpClient {
        return HttpClient.newHttpClient()
    }
}