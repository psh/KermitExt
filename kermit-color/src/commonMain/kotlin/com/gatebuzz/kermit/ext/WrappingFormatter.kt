package com.gatebuzz.kermit.ext

import co.touchlab.kermit.Message
import co.touchlab.kermit.MessageStringFormatter
import co.touchlab.kermit.Severity
import co.touchlab.kermit.Tag

abstract class WrappingFormatter(
    private val messageStringFormatter: MessageStringFormatter
) : MessageStringFormatter {
    override fun formatMessage(severity: Severity?, tag: Tag?, message: Message): String {
        val prefix = prefix(severity, tag, message)
        val content = messageStringFormatter.formatMessage(severity, tag, message)
        val suffix = suffix(severity, tag, message)
        return "${prefix}$content${suffix}"
    }

    open fun prefix(severity: Severity?, tag: Tag?, message: Message): String = ""
    open fun suffix(severity: Severity?, tag: Tag?, message: Message): String = ""
}