package com.eundeang.trajectory.processor.consumer.test

import com.eundeang.trajectory.processor.core.domain.GPS
import com.eundeang.trajectory.processor.osrm.OsrmClient
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import javax.xml.crypto.Data

@Component
class TestConsumer(
    private val client: OsrmClient
) {
    @KafkaListener(topics = arrayOf("test-topic-#{data.agentId}"))
    fun lister(data: GPS) {
        println("⭐\uFE0F 메시지 수신 완료: 0 ")
        client.routeIfPossible(data)
    }
    @KafkaListener(topics = arrayOf("test-topic"), groupId = "foo")
    fun lister1(data: GPS) {
        println("⭐\uFE0F 메시지 수신 완료: 1 ")
        client.routeIfPossible(data)
    }

    @KafkaListener(topics = arrayOf("test-topic"), groupId = "foo")
    fun lister2(data: GPS) {
        println("⭐️ 메시지 수신 완료: 2 ")
        client.routeIfPossible(data)
    }
}