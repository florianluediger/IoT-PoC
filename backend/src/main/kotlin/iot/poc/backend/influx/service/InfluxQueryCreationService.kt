package iot.poc.backend.influx.service

import iot.poc.backend.influx.config.InfluxProperties
import iot.poc.backend.persistence.service.QueryCreationService
import java.time.Instant

class InfluxQueryCreationService(val influxProperties: InfluxProperties) : QueryCreationService {
    override fun createAggregateQuery(
        sensorType: String,
        intervalStart: Instant,
        intervalEnd: Instant,
        valueName: String,
        tags: Map<String, String>,
        operation: String
    ): String {
        val queryBuilder = StringBuilder()
        queryBuilder.append("from(bucket: \"${influxProperties.bucket}\")")
        queryBuilder.append("|> range(start: $intervalStart, stop: $intervalEnd)")
        queryBuilder.append("|> filter(fn: (r) => r._measurement == \"$sensorType\")")
        queryBuilder.append("|> filter(fn: (r) => r._field == \"$valueName\")")
        tags.forEach {
            queryBuilder.append("|> filter(fn: (r) => r.${it.key} == \"${it.value}\")")
        }
        queryBuilder.append("|> $operation()")

        return queryBuilder.toString()
    }
}