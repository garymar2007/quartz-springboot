package com.gary.quartz_springboot.batchprocess

import arrow.core.Either
import arrow.core.raise.either
import com.gary.quartz_springboot.info.TimerInfo
import com.gary.quartz_springboot.jobs.HelloWorldJob
import com.gary.quartz_springboot.timeservice.QuartzSchedulerError
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
            remainingFireCount = 5,
            runForever = false,
            repeatIntervalMs = 2000L,
            initialOffsetMs = 1000L,
            callbackData = "Hello World Job"
        )
        schedulerService.scheduleJob(jobClass, timerInfo)
        logger.info("END :: runHelloWorldJob")
    }

    fun getAllRunningTimers(): List<TimerInfo> {
        logger.info("START :: getAllRunningTimers")
        val result = schedulerService.getAllRunningTimers()
        result.fold(
            { error -> logger.error("Error fetching running timers: $error") },
            { timers -> return timers }
        ).also {
            logger.info("END :: getAllRunningTimers")
        }
        return emptyList()
    }

    fun getTimerById(timerId: String): Either<QuartzSchedulerError, TimerInfo> = either {
        logger.info("START :: getTimerById")
        val result = schedulerService.getTimerById(timerId)
        logger.info("END :: getTimerById")
        return result
    }

    fun updateTimerById(timerId: String, timerInfo: TimerInfo): Either<QuartzSchedulerError, TimerInfo> = either {
        logger.info("START :: updateTimerById")
        val result = schedulerService.updateTimerById(timerId, timerInfo)
        logger.info("END :: updateTimerById")
        return result
    }
}