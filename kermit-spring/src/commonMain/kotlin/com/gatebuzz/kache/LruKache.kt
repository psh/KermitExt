package com.gatebuzz.kache

import kotlin.time.ComparableTimeMark
import kotlin.time.TimeSource

class LruKache<T>(val maxSize: Int = 32) {
    private val timeSrc = TimeSource.Monotonic
    private val store = mutableMapOf<String, TimestampedEntry<T>>()

    fun get(key: String, factory: () -> T): T {
        var existing = store[key]
        if (existing == null) {
            existing = put(key, factory())
        }
        return existing.logger
    }

    fun rawGet(key: String): T? =
        store[key]?.logger

    fun entries(): List<Pair<String, T>> = mutableListOf<Pair<String, T>>().apply {
        addAll(store.entries.map { Pair(it.key, it.value.logger) })
    }

    private fun put(key: String, logger: T): TimestampedEntry<T> {
        val entry = TimestampedEntry(logger, timeSrc.markNow())
        store[key] = entry
        pruneStore()
        return entry
    }

    private fun pruneStore() {
        if (store.size <= maxSize) return

        store.remove(
            store.entries.minBy { it.value.lastAccess }.key
        )
    }

    private data class TimestampedEntry<T>(val logger: T, var lastAccess: ComparableTimeMark)
}