package iot.poc.backend.persistence.entity

import java.math.BigDecimal
import java.time.Instant

/**
 * This is an entity class that holds information about a single datapoint for sensor data.
 * @property sensorType the type of the sensor could be something like "temperature" or "humidity"
 * @property timestamp the timestamp at which the sensor value was read
 * @property tags the tags contain metadata about the sensor which won't change frequently, like the sensor id
 * @property values the values contain sensor readings like the temperature or humidity
 */
data class SensorData(
    val sensorType: String,
    val timestamp: Instant,
    val tags: Map<String, String>,
    val values: Map<String, BigDecimal>
)
