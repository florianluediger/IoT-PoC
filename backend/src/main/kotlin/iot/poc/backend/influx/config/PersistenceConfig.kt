package iot.poc.backend.influx.config

import com.influxdb.client.kotlin.InfluxDBClientKotlin
import iot.poc.backend.influx.mapper.SensorDataMapper
import iot.poc.backend.influx.service.InfluxQueryCreationService
import iot.poc.backend.influx.repository.InfluxSensorRepository
import iot.poc.backend.persistence.service.QueryCreationService
import iot.poc.backend.persistence.repository.SensorRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PersistenceConfig {
    @Bean
    fun persistenceService(
        influxDB: InfluxDBClientKotlin,
        influxProperties: InfluxProperties,
        sensorDataMapper: SensorDataMapper
    ): SensorRepository {
        return InfluxSensorRepository(influxDB, influxProperties, sensorDataMapper)
    }

    @Bean
    fun queryCreationService(influxProperties: InfluxProperties): QueryCreationService {
        return InfluxQueryCreationService(influxProperties)
    }
}