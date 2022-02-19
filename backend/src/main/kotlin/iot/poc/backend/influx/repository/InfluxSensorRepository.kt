package iot.poc.backend.influx.repository

import com.influxdb.client.kotlin.InfluxDBClientKotlin
import iot.poc.backend.dto.SensorData
import iot.poc.backend.influx.config.InfluxProperties
import iot.poc.backend.influx.mapper.SensorDataMapper
import iot.poc.backend.persistence.repository.SensorRepository
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class InfluxSensorRepository(
    private val influxDB: InfluxDBClientKotlin,
    private val influxProperties: InfluxProperties,
    private val sensorDataMapper: SensorDataMapper
) : SensorRepository {

    override fun saveMeasurement(data: SensorData) {
        val pointData = sensorDataMapper.mapSensorDataToPoint(data)
        runBlocking {
            influxDB.getWriteKotlinApi().writePoint(pointData, influxProperties.bucket, influxProperties.org)
        }
    }

    override fun getValueForAggregateQuery(query: String): BigDecimal {
        val resultStream = influxDB.getQueryKotlinApi().query(query, influxProperties.org)
        val resultValue = runBlocking {
            resultStream.receive().value
        }
        if (resultValue is Double)
            return BigDecimal.valueOf(resultValue)
        return BigDecimal.ZERO
    }
}