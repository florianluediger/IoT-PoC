package iot.poc.backend.service

import iot.poc.backend.dto.SensorData
import iot.poc.backend.persistence.repository.SensorRepository
import iot.poc.backend.persistence.service.QueryCreationService
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Instant

@Service
class SensorService(
    val sensorRepository: SensorRepository,
    val queryCreationService: QueryCreationService
) {
    fun saveMeasurement(data: SensorData) {
        sensorRepository.saveMeasurement(data)
    }

    fun calculateAverageSensorValue(
        intervalStart: Instant,
        intervalEnd: Instant,
        sensorType: String,
        tags: Map<String, String>
    ): BigDecimal {

        val query =
            queryCreationService.createAggregateQuery(sensorType, intervalStart, intervalEnd, tags, "mean")

        return sensorRepository.getValueForAggregateQuery(query)
    }
}