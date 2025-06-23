package com.eundeang.trajectory.processor.osrm

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/route")
class RouteController(
    private val osrmClient: OsrmClient
) {
//    @GetMapping
//    fun getRoute(@RequestBody route: ReqRoute): Mono<RouteResult> {
//        return osrmClient.getRoute(
//            startLon =  route.startLon,
//            startLat =  route.startLat,
//            endLon = route.endLon,
//            endLat =  route.endLat
//        )
//    }
}