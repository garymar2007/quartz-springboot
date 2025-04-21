package com.gary.quartz_springboot.jobs

import com.gary.quartz_springboot.info.TimerInfo
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory

class HelloWorldJob: Job {
    private val log = LoggerFactory.getLogger(HelloWorldJob::class.java)

    override fun execute(jobExecutionContext: JobExecutionContext) {
        log.info("START :: HelloWorldJob")
        val jobDataMap = jobExecutionContext.jobDetail.jobDataMap
        val timerInfo: TimerInfo = jobDataMap.get(HelloWorldJob::class.simpleName) as TimerInfo

        log.info("Timer information: $timerInfo")
        log.info("Job callback data: ${timerInfo.callbackData}")
        log.info("END :: HelloWorldJob")
    }
}