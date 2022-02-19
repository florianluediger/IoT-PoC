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

    @Value("\${backend.uri}")
    lateinit var backendUri: String

    var previousTemperature = BigDecimal.valueOf(20)

    val objectMapper = ObjectMapper().findAndRegisterModules()

    @Scheduled(fixedDelay = 1000)
    fun sendRequestEverySecond() {
        val uri = "$backendUri/sensor"
        val bodyAsString = objectMapper.writeValueAsString(createMockData())
        val request = HttpRequest.newBuilder()
            .uri(URI(uri))
            .setHeader("Content-type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(bodyAsString))
            .build()
        httpClient.send(request, BodyHandlers.discarding())
    }

    fun createMockData(): SensorData {
        val sensorType = "temperature"
        val tags = mapOf(Pair("sensorId", "4711"))
        val values = mapOf(Pair("temperature", generateTemperatureValue()))
        return SensorData(sensorType, Instant.now(), tags, values)
    }

    fun generateTemperatureValue(): BigDecimal {
        val temperatureDifference = (Math.random() - 0.5) / 10
        val newTemperature = previousTemperature.add(BigDecimal.valueOf(temperatureDifference))
        previousTemperature = newTemperature
        return newTemperature
    }
}