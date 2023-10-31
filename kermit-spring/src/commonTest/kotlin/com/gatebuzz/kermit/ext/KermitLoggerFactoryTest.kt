package com.gatebuzz.kermit.ext

import org.junit.Test
import kotlin.test.assertEquals

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

    data class Test2(val id: Int)

    companion object {
        data class Test3(val id: Int)
    }
}

internal data class Test1(val id: Int)