package com.gatebuzz.kermit.ext

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.StaticConfig

class Kermit {

    companion object {
        fun builder() : Builder = LoggerBuilder()

        operator fun invoke(block: Builder.() -> Unit): Logger = with(LoggerBuilder()) {
            block(this)
            build()
        }
    }

    interface Builder {
        fun tag(tag: String): Builder
        fun minSeverity(severity: Severity): Builder
        fun setLogWriters(vararg logWriter: LogWriter): Builder
        fun addLogWriter(vararg logWriter: LogWriter): Builder
        operator fun LogWriter.unaryPlus()
        fun build(): Logger
    }

    internal class LoggerBuilder : Builder {
        private var tag: String = ""
        private var logWriters: MutableList<LogWriter> = mutableListOf()
        private var minSeverity: Severity = Severity.Verbose

        override fun tag(tag: String): Builder {
            this.tag = tag
            return this
        }

        override fun minSeverity(severity: Severity): Builder {
            this.minSeverity = severity
            return this
        }

        override fun setLogWriters(vararg logWriter: LogWriter): Builder {
            logWriters = logWriter.toMutableList()
            return this
        }

        override operator fun LogWriter.unaryPlus() {
            logWriters.add(this)
        }

        override fun addLogWriter(vararg logWriter: LogWriter): Builder {
            logWriters.addAll(logWriter)
            return this
        }

        override fun build(): Logger {
            if (logWriters.isEmpty()) throw Exception("At least one log writer is needed")

            return Logger(
                StaticConfig(minSeverity, logWriters.toList())
            )
        }
    }
}
