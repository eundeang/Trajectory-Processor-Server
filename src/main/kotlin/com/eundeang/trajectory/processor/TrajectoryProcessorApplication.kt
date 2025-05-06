package com.eundeang.trajectory.processor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka

@EnableKafka
@SpringBootApplication
class TrajectoryProcessorApplication

fun main(args: Array<String>) {
	runApplication<TrajectoryProcessorApplication>(*args)
}
