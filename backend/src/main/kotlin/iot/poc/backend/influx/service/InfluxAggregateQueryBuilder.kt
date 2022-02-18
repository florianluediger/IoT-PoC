package iot.poc.backend.influx.service

import iot.poc.backend.persistence.service.AggregateQueryBuilder
import java.time.Instant

class InfluxAggregateQueryBuilder : AggregateQueryBuilder {
    var database: String? = null
    var sensorType: String? = null
    var intervalStart: Instant? = null
    var intervalEnd: Instant? = null
    var tags = mutableMapOf<String, String>()
    var operation: String? = null

    override fun database(database: String): AggregateQueryBuilder =
        apply { this.database = database }

    override fun sensorType(sensorType: String): AggregateQueryBuilder =
        apply { this.sensorType = sensorType }

    override fun intervalStart(intervalStart: Instant): AggregateQueryBuilder =
        apply { this.intervalStart = intervalStart }


    override fun intervalEnd(intervalEnd: Instant): AggregateQueryBuilder =
        apply { this.intervalEnd = intervalEnd }


    override fun tag(tagKey: String, tagValue: String): AggregateQueryBuilder =
        apply { this.tags.put(tagKey, tagValue) }


    override fun tags(tags: Map<String, String>): AggregateQueryBuilder =
        apply { this.tags.putAll(tags) }


    override fun operation(operation: String): AggregateQueryBuilder =
        apply { this.operation = operation }


    override fun build(): String {
        TODO("Not yet implemented")
    }
}