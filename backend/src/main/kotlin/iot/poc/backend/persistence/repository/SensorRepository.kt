package iot.poc.backend.persistence.repository

import iot.poc.backend.dto.SensorData
import java.math.BigDecimal

interface SensorRepository {
    fun saveMeasurement(data: SensorData)
    fun getValueForAggregateQuery(query: String): BigDecimal
}