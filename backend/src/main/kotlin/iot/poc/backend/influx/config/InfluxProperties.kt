package iot.poc.backend.influx.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "influx")
data class InfluxProperties(val url: String, val token: String, val org: String, val bucket: String)