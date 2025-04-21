package com.gary.quartz_springboot.batchprocess

import com.gary.quartz_springboot.info.TimerInfo
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/timer")
class BatchController(val batchService: BatchService) {

    @PostMapping("/runhelloworldjob")
    fun runHelloWorldJob() {
        batchService.runHelloWorldJob()
    }

    @GetMapping("/getallrunningtimers")
    fun getAllRunningTimers(): List<TimerInfo> {
        return batchService.getAllRunningTimers()
    }

    @GetMapping("/{timerId}")
    fun getTimerById(@PathVariable timerId: String): TimerInfo? {
        batchService.getTimerById(timerId).fold(
            { error -> return null },
            { timerInfo -> return timerInfo }
        )
    }
}