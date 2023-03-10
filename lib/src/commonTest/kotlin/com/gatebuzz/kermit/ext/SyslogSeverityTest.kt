package com.gatebuzz.kermit.ext

import co.touchlab.kermit.Severity
import kotlin.test.Test
import kotlin.test.assertEquals

class SyslogSeverityTest {
    @Test
    fun criticalSeverity() {
        assertEquals(2, Severity.Assert.syslogdLevel())
    }

    @Test
    fun errorSeverity() {
        assertEquals(3, Severity.Error.syslogdLevel())
    }

    @Test
    fun warningSeverity() {
        assertEquals(4, Severity.Warn.syslogdLevel())
    }

    @Test
    fun infoSeverity() {
        assertEquals(6, Severity.Info.syslogdLevel())
    }

    @Test
    fun debugSeverity() {
        assertEquals(7, Severity.Debug.syslogdLevel())
    }

    @Test
    fun verboseSeverity() {
        assertEquals(8, Severity.Verbose.syslogdLevel())
    }
}