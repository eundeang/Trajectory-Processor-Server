package com.eundeang.trajectory.processor.core.domain

import com.fasterxml.jackson.annotation.JsonProperty
import java.sql.Timestamp

data class GPS(
    @JsonProperty("trip_id")
    val tripId: String,

    @JsonProperty("agent_id")
    val agentId: String,

    val latitude: String,

    val longitude: String,

    val timestamp: Timestamp
)
