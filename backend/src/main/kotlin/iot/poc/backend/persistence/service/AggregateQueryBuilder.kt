package iot.poc.backend.persistence.service

import java.time.Instant

interface AggregateQueryBuilder {
    fun database(database: String): AggregateQueryBuilder
    fun sensorType(sensorType: String): AggregateQueryBuilder
    fun intervalStart(intervalStart: Instant): AggregateQueryBuilder
    fun intervalEnd(intervalEnd: Instant): AggregateQueryBuilder
    fun tag(tagKey: String, tagValue: String): AggregateQueryBuilder
    fun tags(tags: Map<String, String>): AggregateQueryBuilder
    fun operation(operation: String): AggregateQueryBuilder
    fun build(): String
}