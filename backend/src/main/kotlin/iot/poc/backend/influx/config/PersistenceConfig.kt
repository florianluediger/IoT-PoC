package iot.poc.backend.influx.config

import com.influxdb.client.kotlin.InfluxDBClientKotlin
import iot.poc.backend.influx.mapper.InfluxSensorDataMapper
import iot.poc.backend.influx.repository.InfluxSensorRepository
import iot.poc.backend.influx.service.InfluxQueryCreationService
import iot.poc.backend.persistence.repository.SensorRepository
import iot.poc.backend.persistence.service.QueryCreationService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PersistenceConfig {
    @Bean
    fun persistenceService(
        influxDB: InfluxDBClientKotlin,
        influxProperties: InfluxProperties,
        influxSensorDataMapper: InfluxSensorDataMapper
    ): SensorRepository {
        return InfluxSensorRepository(influxDB, influxProperties, influxSensorDataMapper)
    }

    @Bean
    fun queryCreationService(influxProperties: InfluxProperties): QueryCreationService {
        return InfluxQueryCreationService(influxProperties)
    }
}