package com.gary.quartz_springboot.utils

import com.gary.quartz_springboot.info.TimerInfo
import org.quartz.Job
import org.quartz.JobBuilder
import org.quartz.JobDataMap
import org.quartz.JobDetail
import org.quartz.SimpleScheduleBuilder
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import java.util.Date

class TimerUtils {

    companion object {
        fun buildJobDetails(jobClass: Class<out Job>, timerInfo: TimerInfo): JobDetail {
            val jobDataMap = JobDataMap(mapOf<String, TimerInfo>())
            jobDataMap[jobClass.simpleName] = timerInfo

            return JobBuilder
                .newJob(jobClass)
                .withIdentity(jobClass.simpleName)
                .setJobData(jobDataMap)
                .build()
        }

        fun buildTrigger(jobClass: Class<out Job>, timerInfo: TimerInfo): Trigger {
            val builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(timerInfo.repeatIntervalMs)

            if(timerInfo.runForever) {
                builder.repeatForever()
            } else {
                builder.withRepeatCount(timerInfo.totalFireCount.toInt() - 1)
            }

            return TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.simpleName)
                .startNow()
                .withSchedule(builder)
                .startAt(Date(System.currentTimeMillis() + timerInfo.initialOffsetMs))
                .build()
        }
    }
}