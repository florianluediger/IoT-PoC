package iot.poc.backend.sensor.web

import iot.poc.backend.persistence.entity.SensorData
import iot.poc.backend.sensor.service.SensorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.time.Instant
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/sensor")
class SensorDataController(val sensorService: SensorService) {

    val nonTagParametersForAverageSensorData = listOf("intervalStart", "intervalEnd", "sensorType")

    @PostMapping
    fun createSensorData(@RequestBody data: SensorData) {
        sensorService.saveMeasurement(data)
    }

    @GetMapping("average")
    fun calculateAverageSensorValue(
        @RequestParam(required = true) intervalStart: Instant,
        @RequestParam(required = true) intervalEnd: Instant,
        @RequestParam(required = true) sensorType: String,
        request: HttpServletRequest
    ): ResponseEntity<BigDecimal> {
        val tags = request.parameterMap
            .filterValues { it.size == 1 }
            .mapValues { it.value[0] }
            .filterKeys { nonTagParametersForAverageSensorData.contains(it) }

        val averageValue =
            sensorService.calculateAverageSensorValue(intervalStart, intervalEnd, sensorType, tags)

        return ResponseEntity.ok(averageValue)
    }

}