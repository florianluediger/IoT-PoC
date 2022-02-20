package iot.poc.backend.sensor.service

import iot.poc.backend.persistence.repository.SensorRepository
import iot.poc.backend.persistence.service.QueryCreationService
import iot.poc.backend.sensor.const.SensorConst
import iot.poc.backend.sensor.dto.SensorInput
import iot.poc.backend.sensor.mapper.SensorInputMapper
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.Instant

@Service
class SensorService(
    val sensorRepository: SensorRepository,
    val queryCreationService: QueryCreationService,
    val sensorInputMapper: SensorInputMapper
) {
    /**
     * Persist a single sensor reading for a sensor.
     * @param sensorId the id of the sensor
     * @param sensorInput contains some meta information about the sensor and the measured values
     */
    fun saveMeasurement(sensorId: String, sensorInput: SensorInput) {
        val sensorData = sensorInputMapper.mapSensorInputToSensorData(sensorId, sensorInput)
        sensorRepository.saveMeasurement(sensorData)
    }

    /**
     * Calculate the average value for a sensor.
     * @param sensorId the id of the sensor
     * @param intervalStart the beginning timestamp of the requested interval
     * @param intervalEnd the ending timestamp of the requested interval
     * @param sensorType the type of the sensor could be something like "temperature" or "humidity"
     * @param valueName the name of the value that should be targeted by the aggregation
     * @param tags the tags that can be used to filter the data that is used as a base for the aggregation
     */
    fun calculateAverageSensorValue(
        sensorId: String,
        intervalStart: Instant,
        intervalEnd: Instant,
        sensorType: String,
        valueName: String,
        tags: Map<String, String>
    ): BigDecimal {

        // The persistence unit considers the sensorId as a normal tag
        val tagsIncludingId = tags + (SensorConst.sensorIdTagName to sensorId)

        val query =
            queryCreationService.createAggregateQuery(
                sensorType,
                intervalStart,
                intervalEnd,
                valueName,
                tagsIncludingId,
                "mean"
            )

        return sensorRepository.getValueForAggregateQuery(query)
    }
}