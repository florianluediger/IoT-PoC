package iot.poc.backend.persistence.service

interface AggregateQueryBuilderFactory {
    fun getQueryBuilder(): AggregateQueryBuilder
}