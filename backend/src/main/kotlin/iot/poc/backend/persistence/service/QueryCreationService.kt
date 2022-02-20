package iot.poc.backend.persistence.service

import java.time.Instant

/**
 * This interface provides methods to build queries that can be used in the [iot.poc.backend.persistence.repository.SensorRepository].
 */
interface QueryCreationService {

    /**
     * Create an aggregation query that will return an aggregated result when executed.
     * @param sensorType the type of the sensor could be something like "temperature" or "humidity"
     * @param intervalStart the beginning timestamp of the requested interval
     * @param intervalEnd the ending timestamp of the requested interval
     * @param valueName the name of the value that should be targeted by the aggregation
     * @param tags the tags that can be used to filter the data that is used as a base for the aggregation
     * @param operation the aggregation operation which is dependent of what the used datastore supports
     */
    fun createAggregateQuery(
        sensorType: String,
        intervalStart: Instant,
        intervalEnd: Instant,
        valueName: String,
        tags: Map<String, String>,
        operation: String
    ): String
}