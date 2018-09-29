package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.BaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StrTest : BaseTest() {

    @Test
    fun strNoArguments() {
        assertEquals("", """["str"]""".readEval())
    }

    @Test
    fun strNullArgument() {
        assertEquals("null", """["str", null]""".readEval())
    }

    @Test
    fun strSingleArgument() {
        assertEquals("Hello", """["str", "Hello"]""".readEval())
    }

    @Test
    fun str() {
        assertEquals("Hello, world!", """["str", "Hello, ", "world!"]""".readEval())
    }

    @Test
    fun strIntegerArgument() {
        assertEquals("Hello, 7 world!", """["str", "Hello, ", 7, " world!"]""".readEval())
    }
}
