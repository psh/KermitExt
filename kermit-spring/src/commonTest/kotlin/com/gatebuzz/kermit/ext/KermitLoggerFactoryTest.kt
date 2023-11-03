package com.gatebuzz.kermit.ext

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class KermitLoggerFactoryTest {
    @Test
    fun fullClasspath() {
        assertEquals(
            "com.gatebuzz.kermit.ext.Test1",
            classNameAsTag(Test1::class.java, false)
        )
    }

    @Test
    fun fullClasspathFromInner() {
        assertEquals(
            "com.gatebuzz.kermit.ext.KermitLoggerFactoryTest\$Test2",
            classNameAsTag(Test2::class.java, false)
        )
    }

    @Test
    fun fullClasspathFromCompanion() {
        assertEquals(
            "com.gatebuzz.kermit.ext.KermitLoggerFactoryTest\$Companion\$Test3",
            classNameAsTag(Test3::class.java, false)
        )
    }

    @Test
    fun compactClasspath() {
        assertEquals(
            "c.g.k.e.Test1",
            classNameAsTag(Test1::class.java, true)
        )
    }

    @Test
    fun compactClasspathFromInner() {
        assertEquals(
            "c.g.k.e.KermitLoggerFactoryTest\$Test2",
            classNameAsTag(Test2::class.java, true)
        )
    }

    @Test
    fun compactClasspathFromCompanion() {
        assertEquals(
            "c.g.k.e.KermitLoggerFactoryTest\$Companion\$Test3",
            classNameAsTag(Test3::class.java, true)
        )
    }

    @Test
    fun basicConfig() {
        val root = Logger
        root.setMinSeverity(Severity.Verbose)
        assertEquals("", root.tag)
        assertEquals(Severity.Verbose, root.mutableConfig.minSeverity)

        val superman = root.withTag("superman").apply {
            mutableConfig.minSeverity = Severity.Debug
        }
        assertEquals("superman", superman.tag)
        assertEquals(Severity.Debug, superman.mutableConfig.minSeverity)

        val lois = root.withTag("lois").apply {
            mutableConfig.minSeverity = Severity.Info
        }
        assertEquals("lois", lois.tag)
        assertEquals(Severity.Info, lois.mutableConfig.minSeverity)
    }

    @Test
    fun verifyPerLoggerConfig() {
        val root = Logger
        root.setMinSeverity(Severity.Verbose)
        assertEquals(Severity.Verbose, root.mutableConfig.minSeverity)

        val superman = root.withTag("superman").apply {
            mutableConfig.minSeverity = Severity.Debug
        }
        assertEquals(Severity.Debug, superman.mutableConfig.minSeverity, "Superman min severity")
        // assertEquals(Severity.Verbose, root.mutableConfig.minSeverity, "Root logger min severity")

        val lois = root.withTag("lois").apply {
            mutableConfig.minSeverity = Severity.Info
        }
        assertEquals(Severity.Info, lois.mutableConfig.minSeverity, "Lois min severity")
        assertEquals(Severity.Debug, superman.mutableConfig.minSeverity, "Superman min severity")
        // assertEquals(Severity.Verbose, root.mutableConfig.minSeverity, "Root logger min severity")
    }

    data class Test2(val id: Int)

    companion object {
        data class Test3(val id: Int)
    }
}

internal data class Test1(val id: Int)