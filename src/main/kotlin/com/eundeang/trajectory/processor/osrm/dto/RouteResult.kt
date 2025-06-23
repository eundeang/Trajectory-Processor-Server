package com.eundeang.trajectory.processor.osrm.dto

data class RouteResult(
    val routes: List<Route>
)

data class Route(
    val distance: Double,
    val duration: Double
)