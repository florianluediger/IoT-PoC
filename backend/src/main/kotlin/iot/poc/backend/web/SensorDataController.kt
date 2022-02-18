package iot.poc.backend.web

import iot.poc.backend.dto.SensorData
import iot.poc.backend.persistence.service.PersistenceService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/sensor")
class SensorDataController(private val persistenceService: PersistenceService) {
    @PostMapping
    fun createSensorData(@RequestBody data: SensorData) {
        persistenceService.saveMeasurement(data)
    }
}