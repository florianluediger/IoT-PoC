package iot.poc.backend.influx.config

import com.influxdb.client.kotlin.InfluxDBClientKotlin
import com.influxdb.client.kotlin.InfluxDBClientKotlinFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InfluxConfig {

    @Bean
    fun influxDb(influxProperties: InfluxProperties): InfluxDBClientKotlin {
        return InfluxDBClientKotlinFactory.create(influxProperties.url, influxProperties.token.toCharArray())
    }
}