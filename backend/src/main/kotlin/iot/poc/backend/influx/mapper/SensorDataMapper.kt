package iot.poc.backend.influx.mapper

import com.influxdb.client.write.Point
import iot.poc.backend.dto.SensorData
import org.springframework.stereotype.Component

@Component
class SensorDataMapper {
    fun mapSensorDataToPoint(data: SensorData): Point {
        return Point.measurement(data.sensorType).addTags(data.tags).addFields(data.values)
    }
}