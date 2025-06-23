package com.eundeang.trajectory.processor.transmitter.controller

import com.eundeang.trajectory.processor.core.domain.GPS
import com.eundeang.trajectory.processor.producer.test.TestProducer
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import kotlin.concurrent.timer
import kotlin.system.measureTimeMillis

@RestController
class TransmitController(private val producer: TestProducer) {
    /**
     * * 동기 처리
     */
    @PostMapping("/api/v1/transmit/gps")
    fun transmit(@RequestBody gps: GPS): ResponseEntity<String> {
        val time = measureTimeMillis {
            // 측정할 코드
            producer.sendMessage(gps)
        }
        return ResponseEntity.ok().body("실행시간: ${time}")
    }
    @PostMapping("/api/v1/transmit/gps/quick")
    fun transmitV2(@RequestBody gps: GPS): ResponseEntity<String> {

        return ResponseEntity.ok().build()
    }
}