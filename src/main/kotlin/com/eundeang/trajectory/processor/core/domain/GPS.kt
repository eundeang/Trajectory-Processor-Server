package com.eundeang.trajectory.processor.core.domain

data class GPS(
    val tripId: String,

    val agentId: String,

    val latitude: String,

    val longitude: String,

    val timestamp: String
)
