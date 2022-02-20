package iot.poc.backend.sensor.dto

import java.math.BigDecimal
import java.time.Instant

data class SensorInput(
    val sensorType: String,
    val timestamp: Instant,
    val tags: Map<String, String>,
    val values: Map<String, BigDecimal>
)
