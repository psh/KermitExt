package com.gatebuzz.kermit.ext

import co.touchlab.kermit.Severity
import org.springframework.boot.logging.LogLevel

fun Severity?.logLevel(): LogLevel? = when (this) {
    Severity.Assert -> LogLevel.ERROR
    Severity.Error -> LogLevel.ERROR
    Severity.Warn -> LogLevel.WARN
    Severity.Info -> LogLevel.INFO
    Severity.Debug -> LogLevel.DEBUG
    Severity.Verbose -> LogLevel.TRACE
    null -> null
}

fun LogLevel?.severity(): Severity? = when (this) {
    LogLevel.TRACE -> Severity.Verbose
    LogLevel.DEBUG -> Severity.Debug
    LogLevel.INFO -> Severity.Info
    LogLevel.WARN -> Severity.Warn
    LogLevel.ERROR -> Severity.Error
    LogLevel.FATAL -> Severity.Error
    LogLevel.OFF, null -> null
}

