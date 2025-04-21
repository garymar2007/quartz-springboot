package com.gary.quartz_springboot.timeservice

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

    fun scheduleJob() {
        logger.info("Scheduling job")
        // Implement job scheduling logic here
    }

    fun stopScheduler() {
        logger.info("Stopping scheduler")
        scheduler.shutdown()
        // Implement logic to stop the scheduler
    }

}