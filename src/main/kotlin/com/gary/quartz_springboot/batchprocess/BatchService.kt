package com.gary.quartz_springboot.batchprocess

import com.gary.quartz_springboot.info.TimerInfo
import com.gary.quartz_springboot.jobs.HelloWorldJob
import com.gary.quartz_springboot.timeservice.SchedulerService
import org.springframework.stereotype.Service

@Service
class BatchService(val schedulerService: SchedulerService) {
    private val logger = org.slf4j.LoggerFactory.getLogger(BatchService::class.java)

    fun runHelloWorldJob() {
        logger.info("START :: runHelloWorldJob")
        val jobClass = HelloWorldJob::class.java
        val timerInfo = TimerInfo(
            totalFireCount = 5,
            runForever = false,
            repeatIntervalMs = 1000L,
            initialOffsetMs = 1000L,
            callbackData = "Hello World Job"
        )
        schedulerService.scheduleJob(jobClass, timerInfo)
        logger.info("END :: runHelloWorldJob")
    }
}