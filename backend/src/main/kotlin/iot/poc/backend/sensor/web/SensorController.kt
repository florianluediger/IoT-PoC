package iot.poc.backend.sensor.web

import iot.poc.backend.sensor.dto.SensorInput
import iot.poc.backend.sensor.service.SensorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import java.time.Instant
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/sensor")
class SensorController(val sensorService: SensorService) {

    val nonTagParametersForAverageSensorData = listOf("intervalStart", "intervalEnd", "sensorType", "valueName")

    @PostMapping("{sensorId}")
    fun createSensorData(@PathVariable sensorId: String, @RequestBody data: SensorInput) {
        sensorService.saveMeasurement(sensorId, data)
    }

    @GetMapping("{sensorId}/average")
    fun calculateAverageSensorValue(
        @PathVariable sensorId: String,
        @RequestParam(required = true) intervalStart: Instant,
        @RequestParam(required = true) intervalEnd: Instant,
        @RequestParam(required = true) sensorType: String,
        @RequestParam(required = true) valueName: String,
        request: HttpServletRequest
    ): ResponseEntity<BigDecimal> {
        val tags = extractTagsFromRequestParams(request.parameterMap)

        val averageValue =
            sensorService.calculateAverageSensorValue(sensorId, intervalStart, intervalEnd, sensorType, valueName, tags)

        return ResponseEntity.ok(averageValue)
    }

    private fun extractTagsFromRequestParams(parameterMap: Map<String, Array<String>>): Map<String, String> {
        return parameterMap
            .filterValues { it.size == 1 }
            .mapValues { it.value[0] }
            .filterKeys { !nonTagParametersForAverageSensorData.contains(it) }
    }

}