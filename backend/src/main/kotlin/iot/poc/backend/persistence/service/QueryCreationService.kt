package iot.poc.backend.persistence.service

import java.time.Instant

interface QueryCreationService {
    fun createAggregateQuery(
        sensorType: String,
        intervalStart: Instant,
        intervalEnd: Instant,
        tags: Map<String, String>,
        operation: String
    ): String
}