package com.gatebuzz.kermit.ext

import co.touchlab.kermit.Severity

/**
 * See: https://en.wikipedia.org/wiki/Syslog#Severity_level
 */
fun Severity.syslogdLevel(): Int = when (this) {
    Severity.Assert -> 2 // mapped to "critical" in the syslogd definition
    Severity.Error -> 3
    Severity.Warn -> 4
    Severity.Info -> 6
    Severity.Debug -> 7
    Severity.Verbose -> 8 // out of scope with the spec
}