package iot.poc.backend.service

import iot.poc.backend.dto.SensorData
import iot.poc.backend.persistence.service.AggregateQueryBuilderFactory
import iot.poc.backend.persistence.service.SensorRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Instant

@Service
class SensorService(
    val sensorRepository: SensorRepository,
    val aggregateQueryBuilderFactory: AggregateQueryBuilderFactory
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

        val query = aggregateQueryBuilderFactory.getQueryBuilder()
            .intervalStart(intervalStart)
            .intervalEnd(intervalEnd)
            .sensorType(sensorType)
            .tags(tags)
            .operation("mean")
            .build()

        return sensorRepository.getValueForAggregateQuery(query)
    }
}