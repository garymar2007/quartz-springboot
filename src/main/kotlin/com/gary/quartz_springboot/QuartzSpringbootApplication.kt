package com.gary.quartz_springboot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QuartzSpringbootApplication

fun main(args: Array<String>) {
	runApplication<QuartzSpringbootApplication>(*args)
}
