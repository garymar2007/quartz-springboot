package com.gary.quartz_springboot.timeservice

import arrow.core.Either
import arrow.core.raise.either
import com.gary.quartz_springboot.info.TimerInfo
import org.quartz.JobExecutionContext
import org.quartz.Trigger
import org.quartz.TriggerListener

class SimpleTriggerlistener(val schedulerService: SchedulerService) : TriggerListener{
    override fun getName(): String {
        return "SimpleTriggerlistener"
    }

    override fun triggerFired(trigger: Trigger, jobExecutionContext: JobExecutionContext) {
        val timerId = trigger.key.name
        val timerInfo = jobExecutionContext.jobDetail.jobDataMap.get(timerId) as TimerInfo

        if (!timerInfo.runForever) {
            if (timerInfo.remainingFireCount == 0) {
                return
            }
            timerInfo.remainingFireCount = timerInfo.remainingFireCount - 1
        }
        schedulerService.updateTimerById(timerId, timerInfo)
    }

    override fun vetoJobExecution(p0: Trigger?, p1: JobExecutionContext?): Boolean {
        TODO("Not yet implemented")
    }

    override fun triggerMisfired(p0: Trigger?) {
        TODO("Not yet implemented")
    }

    override fun triggerComplete(
        p0: Trigger?,
        p1: JobExecutionContext?,
        p2: Trigger.CompletedExecutionInstruction?
    ) {
        TODO("Not yet implemented")
    }
}