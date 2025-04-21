package com.gary.quartz_springboot.timeservice

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensureNotNull
import com.gary.quartz_springboot.info.TimerInfo
import com.gary.quartz_springboot.utils.TimerUtils
import org.quartz.Job
import org.quartz.JobKey
import org.quartz.Scheduler
import org.quartz.impl.matchers.GroupMatcher
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SchedulerService(scheduler : Scheduler) {
    private val logger = LoggerFactory.getLogger(SchedulerService::class.java)
    private val scheduler = scheduler

    init {
        logger.info("SchedulerService initialized")
        scheduler.start()
        logger.info("Scheduler started")
    }

    fun scheduleJob(jobClass: Class<out Job>, timerInfo: TimerInfo) {
        logger.info("START :: Scheduling a job")
        val jobDetail = TimerUtils.buildJobDetails(jobClass, timerInfo)
        val trigger = TimerUtils.buildTrigger(jobClass, timerInfo)

        scheduler.scheduleJob(jobDetail, trigger)
        logger.info("END :: Scheduler finished")
    }

    fun getAllRunningTimers(): Either<QuartzSchedulerError, List<TimerInfo>>  = either {
        logger.info("START :: Fetching all running timers")
        val jobKeys = scheduler.getJobKeys(GroupMatcher.anyJobGroup())
        ensureNotNull(jobKeys) {
            QuartzSchedulerError(
                errorCode = "SCHEDULER_ERROR",
                errorMessage = "Failed to fetch job keys",
                errorDetails = "Job keys are null"
            )
        }
        if (jobKeys.isEmpty()) {
            logger.info("No running timers found")
            return@either emptyList()
        }

        val timerInfos = jobKeys.mapNotNull { jobKey ->
            scheduler.getJobDetail(jobKey)?.jobDataMap?.get(jobKey.name) as? TimerInfo
        }

        timerInfos
    }

    fun getTimerById(timerId: String): Either<QuartzSchedulerError, TimerInfo> = either {
        logger.info("START :: Fetching timer by ID: $timerId")
        val jobDetail = scheduler.getJobDetail(JobKey(timerId))
        ensureNotNull(jobDetail) {
            QuartzSchedulerError(
                errorCode = "SCHEDULER_ERROR",
                errorMessage = "Failed to fetch job by timer id",
                errorDetails = "Job of timer Id $timerId is not found"
            )
        }

        val timerInfo = jobDetail.jobDataMap.get(timerId) as? TimerInfo
        ensureNotNull(timerInfo) {
            QuartzSchedulerError(
                errorCode = "SCHEDULER_ERROR",
                errorMessage = "Failed to fetch timer info",
                errorDetails = "Timer info for ID $timerId is null"
            )
        }

        timerInfo
    }

    fun stopScheduler() {
        logger.info("Stopping scheduler")
        scheduler.shutdown()
        // Implement logic to stop the scheduler
    }

}

data class QuartzSchedulerError(
    val errorCode: String,
    val errorMessage: String,
    val errorDetails: String? = null
)  {
}
