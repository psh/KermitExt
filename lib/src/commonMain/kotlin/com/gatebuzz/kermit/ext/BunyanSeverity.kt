package com.gatebuzz.kermit.ext

import co.touchlab.kermit.Severity

/**
 * See: https://github.com/trentm/node-bunyan#levels
 */
fun Severity.bunyanLevel(): Int = when (this) {
    Severity.Assert -> 60 // mapped to "fatal" in the bunyan definition
    Severity.Error -> 50
    Severity.Warn -> 40
    Severity.Info -> 30
    Severity.Debug -> 20
    Severity.Verbose -> 10 // mapped to "trace" in the bunyan definition
}