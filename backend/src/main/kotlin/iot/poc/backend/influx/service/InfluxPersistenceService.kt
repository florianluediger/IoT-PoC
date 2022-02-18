package iot.poc.backend.influx.service

import com.influxdb.client.kotlin.InfluxDBClientKotlin
import iot.poc.backend.dto.SensorData
import iot.poc.backend.influx.config.InfluxProperties
import iot.poc.backend.influx.mapper.SensorDataMapper
import iot.poc.backend.persistence.service.PersistenceService
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class InfluxPersistenceService(
    private val influxDB: InfluxDBClientKotlin,
    private val influxProperties: InfluxProperties,
    private val sensorDataMapper: SensorDataMapper
) :
    PersistenceService {
    override fun saveMeasurement(data: SensorData) {
        val pointData = sensorDataMapper.mapSensorDataToPoint(data)
        runBlocking {
            influxDB.getWriteKotlinApi().writePoint(pointData, influxProperties.bucket, influxProperties.org)
        }
    }
}