package com.eundeang.trajectory.processor.transmitter.config.kafka

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaProducerConfig {

    /**
     * 프로듀서 설정을 Map 으로 구성하여 ProducerFactory 를 생성한다.
     */
    fun producerFactory(): ProducerFactory<String, Any> {
        val config = mutableMapOf<String, Any>()
        // 프로듀서가 처음으로 연결할 브로커의 위치를 설정
        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        // key, value 데이터를 Kafka 브로커로 전송하기 전에 데이터를 바이트 배열로 변환하는데 사용하는 직렬화 메커니즘 설정 (카프카는 네트워크를 통해 데이터를 전송하기 때문에, 객체를 바이트 배열로 변환해야 함)
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class

        return DefaultKafkaProducerFactory(config)
    }

    /**
     * ProducerFactory 로 KafkaTemplate 를 만들어 해당 객체를 통해서 Message 를 전송한다.
     * - KafkaTemplate 은 Kafka 에 메시지를 보내는 여러 메서드를 제공하며, 이 메서드를 사용해서 브로커로 메시지를 보내기 위해 직접 Kafka Producer API 를 사용하지 않고, send 와 같은 메서드를 통해 더 편리하고 간결한 코드로 메시지를 보낼 수 있다.
     */
    fun kafkaTemplate(): KafkaTemplate<String, Any> {
        return KafkaTemplate(producerFactory())
    }
}