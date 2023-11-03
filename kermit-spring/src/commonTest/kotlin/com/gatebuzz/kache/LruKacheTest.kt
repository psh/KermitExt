package com.gatebuzz.kache

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull


class LruKacheTest {
    @Test
    fun getEntries() {
        val testObject = LruKache<String>(3)

        testObject.get("1") { "one" }
        testObject.get("2") { "two" }
        testObject.get("3") { "three" }

        val result = testObject.entries().toMap()
        assertEquals(3, result.size)
        assertEquals("one", result["1"])
        assertEquals("two", result["2"])
        assertEquals("three", result["3"])
    }

    @Test
    fun rawGet() {
        val testObject = LruKache<String>(3)

        testObject.get("1") { "one" }

        assertEquals("one", testObject.rawGet("1"))
        assertNull(testObject.rawGet("junk"))
    }

    @Test
    fun pruneCache() {
        val testObject = LruKache<String>(3)

        testObject.get("1") { "one" }
        testObject.get("2") { "two" }
        testObject.get("3") { "three" }
        testObject.get("4") { "four" }

        // Correct item was pruned
        assertNull(testObject.rawGet("1"))

        val result = testObject.entries().toMap()
        assertEquals(3, result.size)
        assertEquals("two", result["2"])
        assertEquals("three", result["3"])
        assertEquals("four", result["4"])
    }
}