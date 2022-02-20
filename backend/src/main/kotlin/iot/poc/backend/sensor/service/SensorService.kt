package iot.poc.backend.sensor.service

import iot.poc.backend.persistence.repository.SensorRepository
import iot.poc.backend.persistence.service.QueryCreationService
import iot.poc.backend.sensor.const.SensorConst
import iot.poc.backend.sensor.dto.SensorInput
import iot.poc.backend.sensor.mapper.SensorInputMapper
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Instant

@Service
class SensorService(
    val sensorRepository: SensorRepository,
    val queryCreationService: QueryCreationService,
    val sensorInputMapper: SensorInputMapper
) {
    fun saveMeasurement(sensorId: String, sensorInput: SensorInput) {
        val sensorData = sensorInputMapper.mapSensorInputToSensorData(sensorId, sensorInput)
        sensorRepository.saveMeasurement(sensorData)
    }

    fun calculateAverageSensorValue(
        sensorId: String,
        intervalStart: Instant,
        intervalEnd: Instant,
        sensorType: String,
        valueName: String,
        tags: Map<String, String>
    ): BigDecimal {

        val tagsIncludingId = tags + (SensorConst.sensorIdTagName to sensorId)

        val query =
            queryCreationService.createAggregateQuery(
                sensorType,
                intervalStart,
                intervalEnd,
                valueName,
                tagsIncludingId,
                "mean"
            )

        return sensorRepository.getValueForAggregateQuery(query)
    }
}