package com.gary.quartz_springboot.batchprocess

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/timer")
class BatchController(val batchService: BatchService) {

    @PostMapping("/runhelloWorldJob")
    fun runHelloWorldJob() {
        batchService.runHelloWorldJob()
    }
}