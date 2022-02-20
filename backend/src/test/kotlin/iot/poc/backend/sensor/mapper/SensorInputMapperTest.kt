package iot.poc.backend.sensor.mapper

import iot.poc.backend.sensor.dto.SensorInput
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.Instant

internal class SensorInputMapperTest {
    private val sensorInputMapper = SensorInputMapper()

    @Test
    internal fun mapSensorInputToSensorData_shouldCreateFullData_whenInputIsComplete() {
        val sensorId = "4711"
        val sensorType = "temperature"
        val timestamp = Instant.parse("2000-01-01T00:00:00Z")
        val tags = mapOf("location" to "office")
        val values = mapOf("temp" to BigDecimal.valueOf(20))

        val sensorInput = SensorInput(sensorType, timestamp, tags, values)

        val result = sensorInputMapper.mapSensorInputToSensorData(sensorId, sensorInput)

        assertThat(result.sensorType).isEqualTo(sensorType)
        assertThat(result.timestamp).isEqualTo(timestamp)
        assertThat(result.tags["location"]).isEqualTo("office")
        assertThat(result.tags["sensorId"]).isEqualTo(sensorId)
        assertThat(result.values).isEqualTo(values)
    }

    @Test
    internal fun mapSensorInputToSensorData_shouldCreateData_whenInputIsMinimal() {
        val sensorId = ""
        val sensorType = ""
        val timestamp = Instant.MIN
        val tags = emptyMap<String, String>()
        val values = emptyMap<String, BigDecimal>()

        val sensorInput = SensorInput(sensorType, timestamp, tags, values)

        val result = sensorInputMapper.mapSensorInputToSensorData(sensorId, sensorInput)

        assertThat(result.sensorType).isEqualTo(sensorType)
        assertThat(result.timestamp).isEqualTo(timestamp)
        assertThat(result.tags["sensorId"]).isEqualTo(sensorId)
        assertThat(result.values).isEqualTo(values)
    }
}