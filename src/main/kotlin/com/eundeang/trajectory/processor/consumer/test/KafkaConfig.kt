package com.example.kafka.config

import com.eundeang.trajectory.processor.core.domain.GPS
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.annotation.PostConstruct
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.KafkaMessageListenerContainer
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import org.springframework.kafka.support.serializer.JsonDeserializer as SpringJsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer as SpringJsonSerializer

@Configuration
class KafkaConfig(
    @Value("\${spring.kafka.bootstrap-servers}") private val bootstrapServers: String
) {

    //--- 1. ProducerFactory / KafkaTemplate -----------------------------------

    @Bean
    fun producerFactory(): ProducerFactory<String, GPS> {
        val props = mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to SpringJsonSerializer::class.java
        )
        return DefaultKafkaProducerFactory(props)
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, GPS> =
        KafkaTemplate(producerFactory())


    //--- 2. ConsumerFactory / ListenerContainerFactory -------------------------

    @Bean
    fun consumerFactory(): ConsumerFactory<String, GPS> {
        val jacksonMapper = jacksonObjectMapper()
        val valueDeserializer = SpringJsonDeserializer(GPS::class.java, jacksonMapper)
            .apply { this.addTrustedPackages("*") }

        val errorHandlingDeserializer = ErrorHandlingDeserializer<GPS>(valueDeserializer)

        val props = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
            ConsumerConfig.GROUP_ID_CONFIG to "default-group",
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to errorHandlingDeserializer::class.java,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "earliest"
        )
        return DefaultKafkaConsumerFactory(props, StringDeserializer(), errorHandlingDeserializer)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, GPS> {
        return ConcurrentKafkaListenerContainerFactory<String, GPS>().apply {
            this.consumerFactory = consumerFactory()
            // 병렬처리 스레드 수 설정(concurrency)
            this.setConcurrency(3)
            this.containerProperties.ackMode = ContainerProperties.AckMode.BATCH
        }
    }


    //--- 3. Dynamic Listener Registrar ---------------------------------------

    /**
     * 애플리케이션 기동 후, 원하는 agentId 리스트만큼 토픽별 컨테이너를 동적으로 띄우고 싶을 때 사용.
     * topics = ["test-topic-agent_0", "test-topic-agent_1", ...]
     */
    @Bean
    fun dynamicKafkaListeners(
        factory: ConcurrentKafkaListenerContainerFactory<String, GPS>
    ): DynamicKafkaListenerRegistrar {
        return DynamicKafkaListenerRegistrar(factory)
    }
}

//--- 동적 리스너 등록 클래스 ------------------------------------------------

class DynamicKafkaListenerRegistrar(
    private val factory: ConcurrentKafkaListenerContainerFactory<String, GPS>
) {

    // 예시용 agentId 목록. 실제로는 DB나 외부 설정에서 로딩
    private val agentIds = listOf("agent_0", "agent_1", "agent_2")

    @PostConstruct
    fun register() {
        agentIds.forEach { agentId ->
            val topic = "test-topic-$agentId"
            val containerProps = ContainerProperties(topic).apply {
                groupId = "foo-$agentId"
            }
            val container: KafkaMessageListenerContainer<String, GPS> =
                factory.createContainer(containerProps)
            container.setupMessageListener { record ->
                // GPS 처리 로직을 직접 호출하거나 서비스로 위임
                println("▶▶ [$agentId] received: ${record.value()}")
            }
            container.start()
        }
    }
}
