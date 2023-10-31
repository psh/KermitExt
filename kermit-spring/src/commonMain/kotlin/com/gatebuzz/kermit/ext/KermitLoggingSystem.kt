package com.gatebuzz.kermit.ext

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.platformLogWriter
import org.springframework.boot.logging.AbstractLoggingSystem
import org.springframework.boot.logging.LogFile
import org.springframework.boot.logging.LogLevel
import org.springframework.boot.logging.LoggerConfiguration
import org.springframework.boot.logging.LoggingInitializationContext
import org.springframework.core.io.UrlResource
import org.springframework.core.io.support.PropertiesLoaderUtils
import org.springframework.util.ResourceUtils
import java.util.Properties

@Suppress("MemberVisibilityCanBePrivate", "unused")
class KermitLoggingSystem(classLoader: ClassLoader) : AbstractLoggingSystem(classLoader) {
    override fun getSupportedLogLevels(): MutableSet<LogLevel> {
        return levels.supported
    }

    override fun setLogLevel(loggerName: String?, level: LogLevel?) {
        setLogLevel(loggerName, levels.convertSystemToNative(level))
    }

    fun setLogLevel(loggerName: String?, severity: Severity?) {
        severity?.let {
            loggerByName(loggerName).mutableConfig.minSeverity = it
        }
    }

    override fun getStandardConfigLocations(): Array<String> =
        arrayOf("kermit.properties")

    override fun loadDefaults(
        initializationContext: LoggingInitializationContext?,
        logFile: LogFile?
    ) = loadConfiguration(
        initializationContext,
        getPackagedConfigFile(
            when {
                logFile != null -> "kermit-file.properties"
                else -> "kermit.properties"
            }
        ), logFile
    )

    override fun getLoggerConfigurations(): MutableList<LoggerConfiguration> {
        return mutableListOf<LoggerConfiguration>().apply {
            add(Logger.toConfig(Logger.tag))
            addAll(KermitLoggerFactory.allLoggers().map {
                it.second.toConfig(it.first)
            })
        }
    }

    override fun getLoggerConfiguration(loggerName: String?): LoggerConfiguration {
        loggerName?.let {
            val logger = KermitLoggerFactory.getOrNull(it) ?: Logger
            return logger.toConfig(logger.tag)
        }

        return Logger.toConfig(Logger.tag)
    }

    override fun loadConfiguration(
        initializationContext: LoggingInitializationContext?,
        location: String?,
        logFile: LogFile?
    ) {
        try {
            val properties = PropertiesLoaderUtils.loadProperties(
                UrlResource(ResourceUtils.getURL(location!!))
            )

            configureFormatter(properties)
            configureTag(properties)
            configureMinSeverity(properties)
        } catch (e: Throwable) {
            e.printStackTrace()
            // Do nothing - Kermit generally works out of the box with defaults
        }
    }

    private fun loggerByName(loggerName: String?): Logger {
        return loggerName?.let {
            KermitLoggerFactory.getOrNull(it) ?: Logger
        } ?: Logger
    }

    private fun Logger.toConfig(name: String): LoggerConfiguration {
        val level = config.minSeverity.logLevel()
        return LoggerConfiguration(name, level, level)
    }

    private fun configureMinSeverity(properties: Properties) {
        val minSeverity = properties.getProperty("min.severity", "")
        if (minSeverity.isNotEmpty()) {
            Severity.entries.forEach {
                if (it.toString().equals(minSeverity, ignoreCase = true)) {
                    Logger.setMinSeverity(it)
                }
            }
        }
    }

    private fun configureTag(properties: Properties) {
        val tag = properties.getProperty("tag.default", "")
        if (tag.isNotEmpty()) {
            Logger.setTag(tag)
        }

        val compact = properties.getProperty("tag.classpath.format", "compact")
        KermitLoggerFactory.compact = compact != "full"
    }

    private fun configureFormatter(properties: Properties) {
        val formatter = when (properties.getProperty("color")) {
            "on" -> withColor(SpringMessageFormatter)
            "bright" -> withBrightColor(SpringMessageFormatter)
            else -> SpringMessageFormatter
        }
        Logger.setLogWriters(platformLogWriter(formatter))
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
