package com.gatebuzz.kermit.ext

import co.touchlab.kermit.DefaultFormatter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.platformLogWriter
import org.springframework.boot.logging.AbstractLoggingSystem
import org.springframework.boot.logging.LogFile
import org.springframework.boot.logging.LogLevel
import org.springframework.boot.logging.LoggingInitializationContext
import org.springframework.core.io.UrlResource
import org.springframework.core.io.support.PropertiesLoaderUtils
import org.springframework.util.ResourceUtils
import java.util.Properties

class KermitLoggingSystem(classLoader: ClassLoader) : AbstractLoggingSystem(classLoader) {
    init {
        println("###> ---------------- --------------------------- ---------------- ")
        println("###> ---------------- KermitLoggingSystem Enabled ---------------- ")
        println("###> ---------------- --------------------------- ---------------- ")
    }

    override fun getSupportedLogLevels(): MutableSet<LogLevel> {
        return levels.supported
    }

    override fun setLogLevel(loggerName: String?, level: LogLevel?) {
        setLogLevel(loggerName, levels.convertSystemToNative(level))
    }

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
        }
    }

    private fun configureMinSeverity(properties: Properties) {
        val minSeverity = properties.getProperty("minSeverity", "")
        if (minSeverity.isNotEmpty()) {
            Severity.entries.forEach {
                if (it.toString().equals(minSeverity, ignoreCase = true)) {
                    Logger.setMinSeverity(it)
                }
            }
        }
    }

    private fun configureTag(properties: Properties) {
        val tag = properties.getProperty("tag", "")
        if (tag.isNotEmpty()) {
            Logger.setTag(tag)
        }
    }

    private fun configureFormatter(properties: Properties) {
        val color: String = properties.getProperty("color")
        val formatter = when (color) {
            "on" -> withColor()
            "bright" -> withBrightColor()
            else -> DefaultFormatter
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