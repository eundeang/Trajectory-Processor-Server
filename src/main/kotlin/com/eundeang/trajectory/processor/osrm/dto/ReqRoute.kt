package com.eundeang.trajectory.processor.osrm.dto

data class ReqRoute(
    val startLon: Double,
    val startLat: Double,
    val endLon: Double,
    val endLat: Double
)
