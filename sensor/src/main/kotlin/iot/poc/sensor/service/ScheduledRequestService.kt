package iot.poc.sensor.service

import com.fasterxml.jackson.databind.ObjectMapper
import iot.poc.sensor.dto.SensorData
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.time.Instant

@Service
class ScheduledRequestService(val httpClient: HttpClient) {

    object SensorParameters {
        val sensorId = "4711"
        val sensorType = "temperature"
        val tags = emptyMap<String, String>()
        val valueName = "temperature"
        val temperatureDifferenceScale = 10
        val initialTemperature = BigDecimal.valueOf(20)
    }

    @Value("\${backend.uri}")
    lateinit var backendUri: String

    var previousTemperature = SensorParameters.initialTemperature

    val objectMapper = ObjectMapper().findAndRegisterModules()

    @Scheduled(fixedDelay = 1000)
    fun sendRequestEverySecond() {
        val uri = "$backendUri/sensor/${SensorParameters.sensorId}"
        val bodyAsString = objectMapper.writeValueAsString(createMockData())
        val request = HttpRequest.newBuilder()
            .uri(URI(uri))
            .setHeader("Content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(bodyAsString))
            .build()
        httpClient.send(request, BodyHandlers.discarding())
    }

    fun createMockData(): SensorData {
        val values = mapOf(SensorParameters.valueName to generateTemperatureValue())
        return SensorData(SensorParameters.sensorType, Instant.now(), SensorParameters.tags, values)
    }

    fun generateTemperatureValue(): BigDecimal {
        val temperatureDifference = (Math.random() - 0.5) / SensorParameters.temperatureDifferenceScale
        val newTemperature = previousTemperature.add(BigDecimal.valueOf(temperatureDifference))
        previousTemperature = newTemperature
        return newTemperature
    }
}