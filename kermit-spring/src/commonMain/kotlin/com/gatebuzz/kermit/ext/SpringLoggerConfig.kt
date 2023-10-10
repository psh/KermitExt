package com.gatebuzz.kermit.ext

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.LoggerConfig
import co.touchlab.kermit.Severity

class SpringLoggerConfig(logWriterList: List<LogWriter>, minSeverity: Severity) : LoggerConfig {
    private val _mutableLogWriters = mutableListOf<LogWriter>()
    private var _mutableMinSeverity : Severity = Severity.Verbose

    override val logWriterList: List<LogWriter>
        get() = _mutableLogWriters
    override val minSeverity: Severity
        get() = _mutableMinSeverity

    init {
        _mutableLogWriters.addAll(logWriterList)
        _mutableMinSeverity = minSeverity
    }

    fun copy() = SpringLoggerConfig(_mutableLogWriters, _mutableMinSeverity)

    companion object {
        fun loggerConfigInit(vararg logWriters: LogWriter, minSeverity: Severity = Severity.Verbose): LoggerConfig =
            SpringLoggerConfig(logWriterList = logWriters.toList(), minSeverity = minSeverity)
    }
}
