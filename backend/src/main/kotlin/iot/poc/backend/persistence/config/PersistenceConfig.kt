package iot.poc.backend.persistence.config

import com.influxdb.client.kotlin.InfluxDBClientKotlin
import iot.poc.backend.influx.config.InfluxProperties
import iot.poc.backend.influx.mapper.SensorDataMapper
import iot.poc.backend.influx.service.InfluxPersistenceService
import iot.poc.backend.persistence.service.PersistenceService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PersistenceConfig {
    @Bean
    fun persistenceService(
        influxDB: InfluxDBClientKotlin,
        influxProperties: InfluxProperties,
        sensorDataMapper: SensorDataMapper
    ): PersistenceService {
        return InfluxPersistenceService(influxDB, influxProperties, sensorDataMapper)
    }
}