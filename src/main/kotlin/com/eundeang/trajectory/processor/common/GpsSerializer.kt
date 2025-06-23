package com.eundeang.trajectory.processor.common

import com.eundeang.trajectory.processor.core.domain.GPS
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.serialization.Serializer

class GpsSerializer : Serializer<GPS> {
    private val objectMapper = ObjectMapper()
    override fun serialize(topic: String?, data: GPS?): ByteArray? {
        return objectMapper.writeValueAsBytes(data)
    }
}