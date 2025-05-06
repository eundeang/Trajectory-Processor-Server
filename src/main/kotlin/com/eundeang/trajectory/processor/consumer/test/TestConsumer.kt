package com.eundeang.trajectory.processor.consumer.test

import org.springframework.kafka.annotation.KafkaListener
import javax.xml.crypto.Data

class TestConsumer {
    @KafkaListener(topics = arrayOf("test-topic"), groupId = "foo")
    fun lister(data: Data) {
        Thread.sleep(1000) // 일부러 느리게 처리
        println(data)
    }
    @KafkaListener(topics = arrayOf("test-topic"), groupId = "foo")
    fun lister1(data: Data) {
        Thread.sleep(1000) // 일부러 느리게 처리
        println(data.toString() + "1")
    }

    @KafkaListener(topics = arrayOf("test-topic"), groupId = "foo")
    fun lister2(data: Data) {
        Thread.sleep(1000) // 일부러 느리게 처리
        println(data.toString() + "2")
    }
}