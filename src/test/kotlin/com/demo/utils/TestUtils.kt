package com.demo.utils

import org.apache.commons.math4.legacy.stat.descriptive.DescriptiveStatistics
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

const val MEASUREMENTS = 20
const val REPEATS = 10_000

fun <T> measureBlock(block: () -> T) {
    val stopWatch = StopWatchKt.createUnstarted()
    val measurements = ArrayList<Duration>(MEASUREMENTS)
    repeat(MEASUREMENTS) {
        stopWatch.reset()
        repeat(REPEATS) {
            stopWatch.record(block)
        }
        stopWatch.durationKt.also(measurements::add).also { println("timeTaken: $it") }
    }
    measurements.printStats()
}

fun List<Duration>.printStats() {
    val stats = DescriptiveStatistics()
    forEach { stats.addValue(it.toDouble(DurationUnit.NANOSECONDS)) }
    println("Mean: ${stats.mean.toDuration(DurationUnit.NANOSECONDS)}")
    println("Median: ${stats.getPercentile(50.0).toDuration(DurationUnit.NANOSECONDS)}")
}
