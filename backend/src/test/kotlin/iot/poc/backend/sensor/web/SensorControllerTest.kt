package iot.poc.backend.sensor.web

import com.fasterxml.jackson.databind.ObjectMapper
import iot.poc.backend.sensor.dto.SensorInput
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.time.Instant

@SpringBootTest
@AutoConfigureMockMvc
internal class SensorControllerTest() {

    @Autowired
    lateinit var mockMvc: MockMvc

    val objectMapper = ObjectMapper().findAndRegisterModules()

    @Test
    internal fun sensorApi_shouldCreateDataAndCalculateCorrectAverage() {
        val sensorId = "4711"
        val sensorType = "temperature"
        val tagKey = "mode"
        val tagValue = "debug"
        val tags = mapOf(tagKey to tagValue)
        val tempKey = "temp"
        val humidityKey = "humidity"

        val timestamp1 = Instant.parse("2000-01-01T01:00:00Z")
        val values1 = mapOf(tempKey to BigDecimal.valueOf(20), humidityKey to BigDecimal.valueOf(80))
        val input1 = SensorInput(sensorType, timestamp1, tags, values1)
        mockMvc.perform(
            post("/sensor/{sensorId}", sensorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input1))
        )
            .andExpect(status().isOk)

        val timestamp2 = Instant.parse("2000-01-01T02:00:00Z")
        val values2 = mapOf(tempKey to BigDecimal.valueOf(22), humidityKey to BigDecimal.valueOf(80))
        val input2 = SensorInput(sensorType, timestamp2, tags, values2)
        mockMvc.perform(
            post("/sensor/{sensorId}", sensorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input2))
        )
            .andExpect(status().isOk)

        val timestamp3 = Instant.parse("2000-01-01T03:00:00Z")
        val values3 = mapOf(tempKey to BigDecimal.valueOf(24), humidityKey to BigDecimal.valueOf(80))
        val input3 = SensorInput(sensorType, timestamp3, tags, values3)
        mockMvc.perform(
            post("/sensor/{sensorId}", sensorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input3))
        )
            .andExpect(status().isOk)

        val expectedString = "22.0"

        val intervalStart = "2000-01-01T00:00:00Z"
        val intervalEnd = "2000-01-01T04:00:00Z"
        mockMvc.perform(
            get("/sensor/{sensorId}/average", sensorId)
                .queryParam("intervalStart", intervalStart)
                .queryParam("intervalEnd", intervalEnd)
                .queryParam("sensorType", sensorType)
                .queryParam("valueName", tempKey)
                .queryParam(tagKey, tagValue)
        ).andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string(expectedString))
    }
}