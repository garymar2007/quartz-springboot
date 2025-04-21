package com.gary.quartz_springboot.info

data class TimerInfo(
    internal val totalFireCount: Int,
    internal var remainingFireCount: Int,
    internal val runForever: Boolean,
    internal val repeatIntervalMs: Long,
    internal val initialOffsetMs: Long,
    internal val callbackData: String,
)