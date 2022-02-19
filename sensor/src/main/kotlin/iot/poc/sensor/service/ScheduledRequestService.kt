package iot.poc.sensor.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ScheduledRequestService {
    @Scheduled(fixedDelay = 1000)
    fun sendRequestEverySecond() {
        TODO("Not implemented yet")
    }
}