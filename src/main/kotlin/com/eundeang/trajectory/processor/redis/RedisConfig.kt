package com.eundeang.trajectory.processor.redis

import com.eundeang.trajectory.processor.core.domain.GPS
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {
    @Bean
    fun reactiveRedisTemplate(
        factory: ReactiveRedisConnectionFactory
    ): ReactiveRedisTemplate<String, GPS> {
        val keySerializer = StringRedisSerializer()
        val valueSerializer = Jackson2JsonRedisSerializer(GPS::class.java)
        val context = RedisSerializationContext
            .newSerializationContext<String, GPS>(keySerializer)
            .value(valueSerializer)
            .build()
        return ReactiveRedisTemplate(factory, context)
    }
}