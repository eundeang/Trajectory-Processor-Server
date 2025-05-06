package com.eundeang.trajectory.processor.producer.test

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import kotlin.concurrent.thread

@Component
class TestProducer(private val kafkaTemplate: KafkaTemplate<String, Any>) {
    fun sendMessage(message: String) {
        for (i in 1..5000) {
            kafkaTemplate.send("test-topic", message)
        }
        println("✅ 메시지 전송 완료: $message")
    }
}