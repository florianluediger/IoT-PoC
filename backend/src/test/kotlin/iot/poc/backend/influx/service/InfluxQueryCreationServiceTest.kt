package iot.poc.backend.influx.service

import iot.poc.backend.influx.config.InfluxProperties
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.Instant

internal class InfluxQueryCreationServiceTest {
    private val bucket = "iot"
    private val influxProperties = InfluxProperties("", "", "", bucket)
    private val influxQueryCreationService = InfluxQueryCreationService(influxProperties)

    @Test
    internal fun createAggregateQuery_shouldCreateCorrectQuery_whenFullDataIsProvided() {
        val sensorType = "temperature"
        val intervalStartText = "2000-01-01T00:00:00Z"
        val intervalEndText = "2000-01-02T00:00:00Z"
        val intervalStart = Instant.parse(intervalStartText)
        val intervalEnd = Instant.parse(intervalEndText)
        val valueName = "temp"
        val sensorIdKey = "sensorId"
        val sensorIdValue = "4711"
        val modeKey = "mode"
        val modeValue = "debug"
        val tags = mapOf(sensorIdKey to sensorIdValue, modeKey to modeValue)
        val operation = "mean"

        val expectedResult =
            "from(bucket: \"$bucket\")|> range(start: $intervalStartText, stop: $intervalEndText)|> filter(fn: (r) => r._measurement == \"$sensorType\")|> filter(fn: (r) => r._field == \"$valueName\")|> filter(fn: (r) => r.$sensorIdKey == \"$sensorIdValue\")|> filter(fn: (r) => r.$modeKey == \"$modeValue\")|> $operation()"

        val result = influxQueryCreationService.createAggregateQuery(
            sensorType,
            intervalStart,
            intervalEnd,
            valueName,
            tags,
            operation
        )

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    internal fun createAggregateQuery_shouldCreateCorrectQuery_whenMinimalDataIsProvided() {
        val sensorType = "temperature"
        val intervalStartText = "2000-01-01T00:00:00Z"
        val intervalEndText = "2000-01-02T00:00:00Z"
        val intervalStart = Instant.parse(intervalStartText)
        val intervalEnd = Instant.parse(intervalEndText)
        val valueName = "temp"
        val tags = emptyMap<String, String>()
        val operation = "mean"

        val expectedResult =
            "from(bucket: \"$bucket\")|> range(start: $intervalStartText, stop: $intervalEndText)|> filter(fn: (r) => r._measurement == \"$sensorType\")|> filter(fn: (r) => r._field == \"$valueName\")|> $operation()"

        val result = influxQueryCreationService.createAggregateQuery(
            sensorType,
            intervalStart,
            intervalEnd,
            valueName,
            tags,
            operation
        )

        assertThat(result).isEqualTo(expectedResult)
    }
}