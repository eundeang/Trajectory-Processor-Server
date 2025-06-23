package com.eundeang.trajectory.processor.producer.test

import com.eundeang.trajectory.processor.core.domain.GPS
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class TestProducer(private val kafkaTemplate: KafkaTemplate<String, Any>) {
    fun sendMessage(gps: GPS) {
        println(LocalDateTime.now())
        kafkaTemplate.send("test-topic-${gps.agentId}", gps.agentId ,gps)
        println("✅ 메시지 전송 완료: $gps")
    }
}