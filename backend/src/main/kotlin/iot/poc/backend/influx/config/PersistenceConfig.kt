package iot.poc.backend.influx.config

import com.influxdb.client.kotlin.InfluxDBClientKotlin
import iot.poc.backend.influx.mapper.SensorDataMapper
import iot.poc.backend.influx.service.InfluxAggregateQueryBuilder
import iot.poc.backend.influx.service.InfluxAggregateQueryBuilderFactory
import iot.poc.backend.influx.service.InfluxSensorRepository
import iot.poc.backend.persistence.service.AggregateQueryBuilder
import iot.poc.backend.persistence.service.AggregateQueryBuilderFactory
import iot.poc.backend.persistence.service.SensorRepository
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
    fun queryBuilder(): AggregateQueryBuilder {
        return InfluxAggregateQueryBuilder()
    }

    @Bean
    fun queryBuilderFactory(influxProperties: InfluxProperties): AggregateQueryBuilderFactory {
        return InfluxAggregateQueryBuilderFactory(influxProperties)
    }
}