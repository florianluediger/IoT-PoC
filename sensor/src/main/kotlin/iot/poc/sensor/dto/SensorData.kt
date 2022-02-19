package iot.poc.sensor.dto

import java.math.BigDecimal
import java.time.Instant

data class SensorData(
    val sensorType: String,
    val timestamp: Instant,
    val tags: Map<String, String>,
    val values: Map<String, BigDecimal>
)
