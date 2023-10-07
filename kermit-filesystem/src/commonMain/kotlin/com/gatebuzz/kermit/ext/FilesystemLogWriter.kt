package com.gatebuzz.kermit.ext

import co.touchlab.kermit.DefaultFormatter
import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Message
import co.touchlab.kermit.MessageStringFormatter
import co.touchlab.kermit.Severity
import co.touchlab.kermit.Tag
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer

expect fun fileSystem(): FileSystem

class FilesystemLogWriter internal constructor(
    private val logPath: String,
    private val logRoller: LogRoller? = null,
    private val formatter: MessageStringFormatter = DefaultFormatter
) : LogWriter() {

    // Not called since we are "Context Aware"
    override fun log(severity: Severity, message: String, tag: String, throwable: Throwable?) {
        val fileSystem = fileSystem()
        val okioPath: Path = logPath.toPath()

        logRoller?.rollLogs(okioPath, fileSystem)

        val sink = fileSystem.appendingSink(okioPath).buffer()

        with(sink) {
            writeUtf8(formatter.formatMessage(severity, Tag(tag), Message(message)))
            writeUtf8("\n")
            throwable?.let {
                writeUtf8(it.stackTraceToString())
            }
            flush()
        }
    }

    companion object {
        operator fun invoke(block: Builder.() -> Unit) =
            with(FilesystemLogWriterBuilder()) {
                block(this)
                build()
            }
    }

    interface Builder {
        fun rollLogAtSize(size: Long): Builder
        fun logPath(path: String): Builder
        fun build(): FilesystemLogWriter
    }

    class FilesystemLogWriterBuilder() : Builder {
        private var maxFileSize: Long? = null
        private var logPath: String? = null

        override fun rollLogAtSize(size: Long): Builder {
            maxFileSize = size
            return this
        }

        override fun logPath(path: String): Builder {
            logPath = path
            return this
        }

        override fun build(): FilesystemLogWriter {
            if (logPath == null) throw NullPointerException("Invalid / missing log path")

            // Can you resist the urge to Rick-roll the logs?
            val rick = maxFileSize?.let {
                FileSizeLogRoller(logPath!!, maxFileSize!!)
            }

            return FilesystemLogWriter(logPath!!, rick)
        }
    }
}