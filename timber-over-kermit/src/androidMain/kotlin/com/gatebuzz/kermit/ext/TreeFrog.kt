package com.gatebuzz.kermit.ext

import android.util.Log
import co.touchlab.kermit.BaseLogger
import co.touchlab.kermit.Severity
import timber.log.Timber

class TreeFrog(private val logger: BaseLogger) : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        logger.log(priority.asSeverity(), tag ?: "", t, message)
    }

    private fun Int.asSeverity() = when (this) {
        Log.ASSERT -> Severity.Assert
        Log.ERROR -> Severity.Error
        Log.WARN -> Severity.Warn
        Log.INFO -> Severity.Info
        Log.DEBUG -> Severity.Debug
        Log.VERBOSE -> Severity.Verbose
        else -> Severity.Verbose
    }
}