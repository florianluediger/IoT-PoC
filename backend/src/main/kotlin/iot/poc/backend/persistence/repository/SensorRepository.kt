package iot.poc.backend.persistence.repository

import iot.poc.backend.persistence.entity.SensorData
import java.math.BigDecimal

/**
 * This interface provides methods to save and access persisted sensor data.
 */
interface SensorRepository {
    /**
     * Save data from a single reading of a sensor, which also contains metadata to identify the sensor.
     * @param data the sensor data contains metadata about the sensor and the values that should be persisted
     */
    fun saveMeasurement(data: SensorData)

    /**
     * Execute an aggregation query and return the result. The query has to be constructed in a way that can only return a single value.
     * @param query the query that should be executed
     * @return the aggregated result of the query
     */
    fun getValueForAggregateQuery(query: String): BigDecimal
}