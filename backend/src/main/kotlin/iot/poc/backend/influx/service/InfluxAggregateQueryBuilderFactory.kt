package iot.poc.backend.influx.service

import iot.poc.backend.influx.config.InfluxProperties
import iot.poc.backend.persistence.service.AggregateQueryBuilder
import iot.poc.backend.persistence.service.AggregateQueryBuilderFactory

class InfluxAggregateQueryBuilderFactory(val influxProperties: InfluxProperties) : AggregateQueryBuilderFactory {
    override fun getQueryBuilder(): AggregateQueryBuilder {
        return InfluxAggregateQueryBuilder().database(influxProperties.bucket)
    }
}