package com.gatebuzz.kermit.ext

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import org.springframework.boot.logging.AbstractLoggingSystem
import org.springframework.boot.logging.LogFile
import org.springframework.boot.logging.LogLevel
import org.springframework.boot.logging.LoggingInitializationContext

class KermitLoggingSystem(classLoader: ClassLoader) : AbstractLoggingSystem(classLoader) {
    override fun getSupportedLogLevels(): MutableSet<LogLevel> {
        return levels.supported
    }

    override fun setLogLevel(loggerName: String?, level: LogLevel?) =
        setLogLevel(loggerName, levels.convertSystemToNative(level))

    fun setLogLevel(loggerName: String?, severity: Severity?) {
        // Note: config MUST always be mutable, and each logger needs its own COPY
        severity?.let {
            loggerByName(loggerName).mutableConfig.minSeverity = it
        }
    }

    private fun loggerByName(loggerName: String?): Logger {
        return if (loggerName.isNullOrBlank()) {
            // No name is our root Kermit logger
            Logger
        } else {
            // Look up the logger by "name" (is that tag?)
            Logger
        }
    }

    override fun getStandardConfigLocations(): Array<String> {
        TODO("Not yet implemented")
    }

    override fun loadDefaults(
        initializationContext: LoggingInitializationContext?,
        logFile: LogFile?
    ) {
        TODO("Not yet implemented")
    }

    override fun loadConfiguration(
        initializationContext: LoggingInitializationContext?,
        location: String?,
        logFile: LogFile?
    ) {
        TODO("Not yet implemented")
    }

    companion object {
        private val levels: LogLevels<Severity> = LogLevels<Severity>().apply {
            map(LogLevel.TRACE, Severity.Verbose)
            map(LogLevel.DEBUG, Severity.Debug)
            map(LogLevel.INFO, Severity.Info)
            map(LogLevel.WARN, Severity.Warn)
            map(LogLevel.ERROR, Severity.Error)
            map(LogLevel.FATAL, Severity.Error)
        }
    }
}