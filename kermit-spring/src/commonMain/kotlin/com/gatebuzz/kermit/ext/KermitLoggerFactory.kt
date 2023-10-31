package com.gatebuzz.kermit.ext

import co.touchlab.kermit.Logger
import com.gatebuzz.kache.LruKache

@Suppress("MemberVisibilityCanBePrivate", "unused")
object KermitLoggerFactory {
    private val loggerCache = LruKache<Logger>()
    var compact: Boolean = true

    fun get(): Logger = Logger

    fun getOrNull(tag: String): Logger? =
        loggerCache.rawGet(tag)

    fun allLoggers(): List<Pair<String, Logger>> =
        loggerCache.entries().sortedBy { it.first }

    fun withTag(tag: String?): Logger = tag?.let {
        loggerCache.get(it) { Logger.withTag(it) }
    } ?: Logger

    fun withTag(clazz: Class<*>): Logger =
        withTag(classNameAsTag(clazz, compact))
}

internal fun classNameAsTag(clazz: Class<*>, compactRepresentation: Boolean): String {
    val parts = clazz.name.split(".")
    val className = parts.last()
    val classPath = parts.dropLast(1)
    val newTag = buildString {
        classPath.forEach {
            val segment = when {
                compactRepresentation -> it.substring(0, 1)
                else -> it
            }
            append(segment).append(".")
        }
        append(className)
    }
    return newTag
}
