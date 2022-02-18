package iot.poc.backend.influx.mapper

import iot.poc.backend.dto.SensorData
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
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
        val fieldValue = 20L
        val input = SensorData(
            type,
            timestamp,
            Collections.singletonMap(tagKey, tagValue),
            Collections.singletonMap(fieldKey, fieldValue)
        )

        val expectedResult =
            "%s,%s=%s %s=%di %d".format(type, tagKey, tagValue, fieldKey, fieldValue, timestamp.epochSecond)

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