package iot.poc.backend

import com.influxdb.client.kotlin.InfluxDBClientKotlin
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class BackendApplicationTests {

	@MockBean
	lateinit var influxDBClientKotlin: InfluxDBClientKotlin

	@Test
	fun contextLoads() {
	}

}
