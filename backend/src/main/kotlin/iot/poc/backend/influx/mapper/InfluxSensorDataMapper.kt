package iot.poc.backend.influx.mapper

import com.influxdb.client.domain.WritePrecision
import com.influxdb.client.write.Point
import iot.poc.backend.persistence.entity.SensorData
import org.springframework.stereotype.Component

@Component
class InfluxSensorDataMapper {
    fun mapSensorDataToPoint(data: SensorData): Point {
        return Point.measurement(data.sensorType)
            .time(data.timestamp, WritePrecision.S)
            .addTags(data.tags)
            .addFields(data.values)
    }
}