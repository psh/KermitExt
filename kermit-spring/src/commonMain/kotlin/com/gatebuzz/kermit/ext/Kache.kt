package com.gatebuzz.kermit.ext

import co.touchlab.kermit.Logger

internal class Kache {
    private val store = mutableMapOf<String, Logger>()
    fun get(key: String, filler: () -> Logger): Logger {
        var existing = store[key]
        if (existing == null) {
            existing = filler()
            store[key] = existing
        }
        return existing
    }

    fun rawGet(key: String) =
        store[key]

    fun entries(): List<Pair<String, Logger>> = mutableListOf<Pair<String, Logger>>().apply {
        addAll(store.entries.map { Pair(it.key, it.value) })
    }
}