package com.eundeang.trajectory.processor.transmitter.controller

import com.eundeang.trajectory.processor.core.domain.GPS
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TransmitController {
    @PostMapping("/api/v1/transmit/gps")
    fun transmit(@RequestBody gps: GPS): ResponseEntity<String> {
        return ResponseEntity.ok().build()
    }
}