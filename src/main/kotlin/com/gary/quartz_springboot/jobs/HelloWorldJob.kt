package com.gary.quartz_springboot.jobs

import org.quartz.Job
import org.quartz.JobExecutionContext
import org.slf4j.LoggerFactory

class HelloWorldJob: Job {
    private val log = LoggerFactory.getLogger(HelloWorldJob::class.java)

    override fun execute(jobExecutionContext: JobExecutionContext?) {
        log.info("Hello World")
    }
}