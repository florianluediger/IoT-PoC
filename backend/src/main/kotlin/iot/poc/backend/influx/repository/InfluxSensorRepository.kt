package iot.poc.backend.influx.repository

import com.influxdb.client.kotlin.InfluxDBClientKotlin
import iot.poc.backend.influx.config.InfluxProperties
import iot.poc.backend.influx.mapper.InfluxSensorDataMapper
import iot.poc.backend.persistence.entity.SensorData
import iot.poc.backend.persistence.exception.PersistenceException
import iot.poc.backend.persistence.repository.SensorRepository
import kotlinx.coroutines.runBlocking
import java.math.BigDecimal

class InfluxSensorRepository(
    private val influxDB: InfluxDBClientKotlin,
    private val influxProperties: InfluxProperties,
    private val influxSensorDataMapper: InfluxSensorDataMapper
) : SensorRepository {

    override fun saveMeasurement(data: SensorData) {
        val pointData = influxSensorDataMapper.mapSensorDataToPoint(data)
        runBlocking {
            influxDB.getWriteKotlinApi().writePoint(pointData, influxProperties.bucket, influxProperties.org)
        }
    }

    override fun getValueForAggregateQuery(query: String): BigDecimal {
        val resultIterator = influxDB.getQueryKotlinApi().query(query, influxProperties.org).iterator()

        val result = runBlocking {
            if (resultIterator.hasNext())
                resultIterator.next()
            else
                throw PersistenceException("No matching entities could be found.")
        }
        val resultValue = result.value
        if (resultValue is Double)
            return BigDecimal.valueOf(resultValue)
        throw PersistenceException("The result from the database could not be interpreted correctly.")
    }
}