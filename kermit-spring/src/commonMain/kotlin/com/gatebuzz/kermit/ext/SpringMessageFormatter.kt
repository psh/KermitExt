package com.gatebuzz.kermit.ext

import co.touchlab.kermit.Message
import co.touchlab.kermit.MessageStringFormatter
import co.touchlab.kermit.Severity
import co.touchlab.kermit.Tag
import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.TimeZone.Companion.currentSystemDefault
import kotlinx.datetime.toLocalDateTime

object SpringMessageFormatter : MessageStringFormatter {
    override fun formatMessage(severity: Severity?, tag: Tag?, message: Message): String {
        return buildString {
            timestamp()

            append(" [").threadName().append("]")

            severity?.let {
                append(" ").append("$it")
            }

            tag?.let {
                if (it.tag.isNotEmpty()) {
                    append(" ").append(it.tag)
                }
            }

            append(" -- ").append(message.message)
        }
    }

    private fun StringBuilder.threadName(): StringBuilder {
        append(Thread.currentThread().name)
        return this
    }

    private fun StringBuilder.timestamp(): StringBuilder {
        val now = now().toLocalDateTime(currentSystemDefault())

        val hour = now.hour
        val min = now.minute
        val second = now.second
        val milli = now.nanosecond / 1000000

        append(if (hour < 10) "0" else "")
        append("${hour}:")
        append(if (min < 10) "0" else "")
        append("${min}:")
        append(if (second < 10) "0" else "")
        append("${second}.")
        append(if (milli < 10) "00" else if (milli < 100) "0" else "")
        append(milli)

        return this
    }
}