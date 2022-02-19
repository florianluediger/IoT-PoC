package iot.poc.backend.influx.mapper

import iot.poc.backend.persistence.entity.SensorData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.Instant
import java.util.*

internal class SensorDataMapperTest {

    private val sensorDataMapper: SensorDataMapper = SensorDataMapper()

    @Test
    internal fun mapSensorDataToPoint_shouldCreatePoint_whenCompleteDataIsProvided() {
        val type = "Temperature"
        val timestamp = Instant.parse("2000-01-01T01:00:00.00Z")
        val tagKey = "sensorId"
        val tagValue = "4711"
        val fieldKey = "temperature"
        val fieldValue = BigDecimal.valueOf(20)
        val input = SensorData(
            type,
            timestamp,
            Collections.singletonMap(tagKey, tagValue),
            Collections.singletonMap(fieldKey, fieldValue)
        )

        Locale.setDefault(Locale.US) // needed so the decimal point is a dot instead of a comma
        val expectedResult =
            "%s,%s=%s %s=%.1f %d".format(type, tagKey, tagValue, fieldKey, fieldValue.toFloat(), timestamp.epochSecond)

        val result = sensorDataMapper.mapSensorDataToPoint(input)

        assertThat(result.toLineProtocol()).isEqualTo(expectedResult)
    }

    @Test
    internal fun mapSensorDataToPoint_shouldCreateEmptyPoint_whenProvidedDataIsEmpty() {
        val input = SensorData("", Instant.MIN, Collections.emptyMap(), Collections.emptyMap())
        val result = sensorDataMapper.mapSensorDataToPoint(input)
        val expectedResult = ""
        assertThat(result.toLineProtocol()).isEqualTo(expectedResult)
    }
}