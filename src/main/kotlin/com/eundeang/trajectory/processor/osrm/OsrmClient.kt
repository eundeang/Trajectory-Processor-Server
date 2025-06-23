package com.eundeang.trajectory.processor.osrm

import com.eundeang.trajectory.processor.core.domain.GPS
import com.eundeang.trajectory.processor.osrm.dto.RouteResult
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class OsrmClient(private val redis: ReactiveRedisTemplate<String, GPS>) {
    private val webClient = WebClient.builder()
        .baseUrl("http://localhost:5003") // OSRM Docker 컨테이너가 열려 있는 포트
        .build()

    fun getRoute(
        prev: GPS,
        now: GPS
    ): Mono<RouteResult> {
        val coordinates = "${prev.longitude},${prev.latitude};${now.longitude}.,${now.latitude}"
        val result = webClient.get()
            .uri("/route/v1/driving/{coordinates}?overview=false", coordinates)
            .retrieve()
            .bodyToMono(RouteResult::class.java)
        println("호출되었음 + ${result}")

        return result
    }

    /**
     * 새 GPS가 들어오면
     * 1) Redis에서 이전 좌표 조회
     * 2) 이전이 있으면 OSRM에 /route 요청
     * 3) 항상 Redis에 현재 좌표 저장
     */
    fun routeIfPossible(now: GPS): Mono<RouteResult> {
        val key = now.agentId

        // 1) 이전 좌표 조회
        return redis.opsForValue().get(key)
            .flatMap { prev ->
                // 2) OSRM 호출
                getRoute(prev, now)
            }
            .switchIfEmpty(Mono.empty())
            // 3) 마무리로 현재 좌표 저장
            .flatMap { resp ->
                redis.opsForValue().set(key, now)
                    .thenReturn(resp)
            }
            // 이전 좌표가 없어서 switchIfEmpty 를 탄 경우라도, 저장은 해줘야 하므로
            .switchIfEmpty(
                redis.opsForValue().set(key, now)
                    .then(Mono.empty())
            )
    }
}