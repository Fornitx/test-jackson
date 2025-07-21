package com.demo.utils

import com.google.common.base.Stopwatch
import java.time.Duration
import kotlin.time.toKotlinDuration

class StopWatchKt(private val stopwatch: Stopwatch) {
    val duration: Duration
        get() = stopwatch.elapsed()
    val durationKt: kotlin.time.Duration
        get() = stopwatch.elapsed().toKotlinDuration()

    fun start(): StopWatchKt = this.also { stopwatch.start() }

    fun stop(): StopWatchKt = this.also { stopwatch.stop() }

    fun reset(): StopWatchKt = this.also { stopwatch.reset() }

    fun <T> record(block: () -> T): T {
        stopwatch.start()
        try {
            return block()
        } finally {
            stopwatch.stop()
        }
    }

    override fun toString(): String = stopwatch.toString()

    companion object {
        fun createUnstarted(): StopWatchKt = StopWatchKt(Stopwatch.createUnstarted())
//        fun createStarted(): StopWatchKt = StopWatchKt(Stopwatch.createStarted())
    }
}
