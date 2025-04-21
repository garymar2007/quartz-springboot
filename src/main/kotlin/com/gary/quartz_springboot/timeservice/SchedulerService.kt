package com.gary.quartz_springboot.timeservice

import com.gary.quartz_springboot.info.TimerInfo
import com.gary.quartz_springboot.utils.TimerUtils
import org.quartz.Job
import org.quartz.Scheduler
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

    fun stopScheduler() {
        logger.info("Stopping scheduler")
        scheduler.shutdown()
        // Implement logic to stop the scheduler
    }

}