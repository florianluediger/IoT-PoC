package iot.poc.backend.sensor.mapper

import iot.poc.backend.persistence.entity.SensorData
import iot.poc.backend.sensor.const.SensorConst
import iot.poc.backend.sensor.dto.SensorInput
import org.springframework.stereotype.Component

@Component
class SensorInputMapper {
    fun mapSensorInputToSensorData(sensorId: String, sensorInput: SensorInput): SensorData {
        // The persistence unit considers the sensorId as a normal tag
        val tagsIncludingId = sensorInput.tags + (SensorConst.sensorIdTagName to sensorId)
        return SensorData(sensorInput.sensorType, sensorInput.timestamp, tagsIncludingId, sensorInput.values)
    }
}