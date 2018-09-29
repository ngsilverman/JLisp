package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.BaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EvalTest : BaseTest() {

    @Test
    fun evalNull() {
        assertEquals(null, """["eval", null]""".readEval())
    }

    @Test
    fun evalString() {
        assertEquals("Hello, world!", """["eval", "Hello, world!"]""".readEval())
    }

    @Test
    fun evalObject() {
        assertEquals(mapOf("key" to 2L), """["eval", {"key": ["+", 1, 1]}]""".readEval())
    }
}
