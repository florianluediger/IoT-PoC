package iot.poc.backend.persistence.service

import iot.poc.backend.dto.SensorData

interface PersistenceService {
    fun saveMeasurement(data: SensorData)
}