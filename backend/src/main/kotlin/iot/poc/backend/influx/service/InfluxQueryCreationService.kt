package iot.poc.backend.influx.service

import iot.poc.backend.persistence.service.QueryCreationService
import java.time.Instant

class InfluxQueryCreationService : QueryCreationService {
    override fun createAggregateQuery(
        sensorType: String,
        intervalStart: Instant,
        intervalEnd: Instant,
        tags: Map<String, String>,
        operation: String
    ): String {
        TODO("Not yet implemented")
    }
}