package com.eundeang.trajectory.processor.transmitter.controller.test

import com.eundeang.trajectory.processor.producer.test.TestProducer
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/kafka")
class KafkaTestController(
    private val producer: TestProducer
) {

//    @PostMapping("/send")
//    fun send(@RequestParam msg: String): String {
//        producer.sendMessage(msg)
//        return "메시지 전송: $msg"
//    }
}