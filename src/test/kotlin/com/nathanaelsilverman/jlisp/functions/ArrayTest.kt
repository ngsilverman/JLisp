package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.BaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ArrayTest : BaseTest() {

    @Test
    fun array() {
        assertEquals(listOf(1L, 2L, 3L), """["array", 1, 2, 3]""".readEval())
    }

    /**
     * Tests that the content of the array is evaluated.
     */
    @Test
    fun arrayEval() {
        assertEquals(listOf(2L, 3L, 4L), """["array", ["+", 1, 1], 3, 4]""".readEval())
    }
}
