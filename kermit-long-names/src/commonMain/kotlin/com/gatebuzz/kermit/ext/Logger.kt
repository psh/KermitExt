package com.gatebuzz.kermit.ext

import co.touchlab.kermit.BaseLogger
import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.LoggerConfig
import co.touchlab.kermit.Severity
import co.touchlab.kermit.Severity.Assert
import co.touchlab.kermit.Severity.Debug
import co.touchlab.kermit.Severity.Error
import co.touchlab.kermit.Severity.Info
import co.touchlab.kermit.Severity.Verbose
import co.touchlab.kermit.Severity.Warn
import co.touchlab.kermit.mutableLoggerConfigInit
import co.touchlab.kermit.platformLogWriter
import kotlin.jvm.JvmOverloads

open class Logger(
    config: LoggerConfig,
    open val tag: String = ""
) : BaseLogger(config) {
    fun withTag(tag: String): Logger {
        return Logger(this.config, tag)
    }

    @JvmOverloads
    inline fun verbose(throwable: Throwable? = null, tag: String = this.tag, message: () -> String) {
        logBlock(Verbose, tag, throwable, message)
    }

    @JvmOverloads
    inline fun debug(throwable: Throwable? = null, tag: String = this.tag, message: () -> String) {
        logBlock(Debug, tag, throwable, message)
    }

    @JvmOverloads
    inline fun info(throwable: Throwable? = null, tag: String = this.tag, message: () -> String) {
        logBlock(Info, tag, throwable, message)
    }

    @JvmOverloads
    inline fun warn(throwable: Throwable? = null, tag: String = this.tag, message: () -> String) {
        logBlock(Warn, tag, throwable, message)
    }

    @JvmOverloads
    inline fun error(throwable: Throwable? = null, tag: String = this.tag, message: () -> String) {
        logBlock(Error, tag, throwable, message)
    }

    @JvmOverloads
    inline fun assert(throwable: Throwable? = null, tag: String = this.tag, message: () -> String) {
        logBlock(Assert, tag, throwable, message)
    }

    @JvmOverloads
    inline fun verbose(messageString: String, throwable: Throwable? = null, tag: String = this.tag) {
        log(Verbose, tag, throwable, messageString)
    }

    @JvmOverloads
    inline fun debug(messageString: String, throwable: Throwable? = null, tag: String = this.tag) {
        log(Debug, tag, throwable, messageString)
    }

    @JvmOverloads
    inline fun info(messageString: String, throwable: Throwable? = null, tag: String = this.tag) {
        log(Info, tag, throwable, messageString)
    }

    @JvmOverloads
    inline fun warn(messageString: String, throwable: Throwable? = null, tag: String = this.tag) {
        log(Warn, tag, throwable, messageString)
    }

    @JvmOverloads
    inline fun error(messageString: String, throwable: Throwable? = null, tag: String = this.tag) {
        log(Error, tag, throwable, messageString)
    }

    @JvmOverloads
    inline fun assert(messageString: String, throwable: Throwable? = null, tag: String = this.tag) {
        log(Assert, tag, throwable, messageString)
    }

    @Suppress("unused")
    companion object : Logger(mutableLoggerConfigInit(listOf(platformLogWriter())), "") {
        override val tag: String
            get() = defaultTag

        fun setMinSeverity(severity: Severity) {
            mutableConfig.minSeverity = severity
        }

        fun setLogWriters(logWriters: List<LogWriter>) {
            mutableConfig.logWriterList = logWriters
        }

        fun setLogWriters(vararg logWriter: LogWriter) {
            mutableConfig.logWriterList = logWriter.toList()
        }

        fun addLogWriter(vararg logWriter: LogWriter) {
            mutableConfig.logWriterList = logWriter.toList() + mutableConfig.logWriterList
        }

        fun setTag(tag: String) {
            defaultTag = tag
        }
    }
}

// Does this really need to be modified in a synchronized { ... } block?
internal var defaultTag: String = ""
