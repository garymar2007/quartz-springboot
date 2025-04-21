package com.gary.quartz_springboot.info

data class TimerInfo(
    internal val totalFireCount: Integer,
    internal val runForever: Boolean,
    internal val repeatIntervalMs: Long,
    internal val initialOffsetMs: Long,
    internal val callbackData: String,
)