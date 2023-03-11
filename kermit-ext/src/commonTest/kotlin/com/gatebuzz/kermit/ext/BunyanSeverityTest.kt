package com.gatebuzz.kermit.ext

import co.touchlab.kermit.Severity
import kotlin.test.Test
import kotlin.test.assertEquals

class BunyanSeverityTest {
    @Test
    fun criticalSeverity() {
        assertEquals(60, Severity.Assert.bunyanLevel())
    }

    @Test
    fun errorSeverity() {
        assertEquals(50, Severity.Error.bunyanLevel())
    }

    @Test
    fun warningSeverity() {
        assertEquals(40, Severity.Warn.bunyanLevel())
    }

    @Test
    fun infoSeverity() {
        assertEquals(30, Severity.Info.bunyanLevel())
    }

    @Test
    fun debugSeverity() {
        assertEquals(20, Severity.Debug.bunyanLevel())
    }

    @Test
    fun verboseSeverity() {
        assertEquals(10, Severity.Verbose.bunyanLevel())
    }
}