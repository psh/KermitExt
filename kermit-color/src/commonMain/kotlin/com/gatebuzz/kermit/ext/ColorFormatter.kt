@file:Suppress("unused")

package com.gatebuzz.kermit.ext

import co.touchlab.kermit.DefaultFormatter
import co.touchlab.kermit.Message
import co.touchlab.kermit.MessageStringFormatter
import co.touchlab.kermit.Severity
import co.touchlab.kermit.Tag
import org.fusesource.jansi.Ansi

fun withColor(messageStringFormatter: MessageStringFormatter = DefaultFormatter): MessageStringFormatter {
    return ColorFormatter(messageStringFormatter)
}

fun withBrightColor(messageStringFormatter: MessageStringFormatter = DefaultFormatter): MessageStringFormatter {
    return BrightColorFormatter(messageStringFormatter)
}

class ColorFormatter(
    messageStringFormatter: MessageStringFormatter = DefaultFormatter
) : WrappingFormatter(messageStringFormatter) {
    override fun prefix(severity: Severity?, tag: Tag?, message: Message) =
        severity?.toAnsiColor() ?: ""

    override fun suffix(severity: Severity?, tag: Tag?, message: Message) =
        resetColor()
}

class BrightColorFormatter(
    messageStringFormatter: MessageStringFormatter = DefaultFormatter
) : WrappingFormatter(messageStringFormatter) {
    override fun prefix(severity: Severity?, tag: Tag?, message: Message) =
        severity?.toBrightAnsiColor() ?: ""

    override fun suffix(severity: Severity?, tag: Tag?, message: Message) = resetColor()
}

internal fun Severity.toBrightAnsiColor() = "${Ansi.ansi().fgBright(this.asColor())}"

internal fun Severity.toAnsiColor() = "${Ansi.ansi().fg(this.asColor())}"

internal fun resetColor() = "${Ansi.ansi().a(Ansi.Attribute.RESET)}"

internal fun Severity.asColor(): Ansi.Color = when (this) {
    Severity.Verbose -> Ansi.Color.WHITE
    Severity.Debug -> Ansi.Color.CYAN
    Severity.Info -> Ansi.Color.GREEN
    Severity.Warn -> Ansi.Color.YELLOW
    Severity.Error -> Ansi.Color.RED
    Severity.Assert -> Ansi.Color.MAGENTA
}
