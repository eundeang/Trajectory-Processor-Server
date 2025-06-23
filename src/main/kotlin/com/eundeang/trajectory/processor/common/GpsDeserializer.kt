package com.eundeang.trajectory.processor.common

import com.eundeang.trajectory.processor.core.domain.GPS
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.kafka.common.serialization.Deserializer
import org.slf4j.LoggerFactory

class GpsDeserializer : Deserializer<GPS> {

    private val objectMapper = jacksonObjectMapper()

    companion object {
        private val logger = LoggerFactory.getLogger(GpsDeserializer::class.java)
    }

    override fun deserialize(topic: String?, data: ByteArray?): GPS? {
        if (data == null) return null
        val json = String(data, Charsets.UTF_8)
        return try {
            objectMapper.readValue(data, GPS::class.java)
        } catch (e: Exception) {
            logger.error("Failed to parse GPS JSON: $json", e)
            null
        }
    }

    override fun close() {
        // Nothing to close
    }
}