package com.gatebuzz.kermit.ext

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.Severity.*
import org.slf4j.LoggerFactory
import org.slf4j.event.Level.*
import org.slf4j.spi.LoggingEventBuilder

class Slf4jLogWriter : LogWriter() {
    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
        LoggerFactory.getLogger(tag)
            .withSeverity(severity)
            .withThrowable(throwable)
            .log(message)
    }

    private fun org.slf4j.Logger.withSeverity(severity: Severity) = atLevel(
        when (severity) {
            Verbose -> TRACE
            Debug -> DEBUG
            Info -> INFO
            Warn -> WARN
            Error -> ERROR
            Assert -> ERROR
        }
    )

    private fun LoggingEventBuilder.withThrowable(throwable: Throwable?): LoggingEventBuilder =
        if (throwable != null) {
            setCause(throwable)
        } else this
}

fun Logger.withTag(clazz: Class<*>) = withTag(clazz.name)