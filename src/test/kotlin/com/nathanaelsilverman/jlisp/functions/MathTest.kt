package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.BaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MathTest : BaseTest() {

    @Test
    fun plus2Arguments() {
        assertEquals(2, """["+", 1, 1]""".readEval())
    }

    @Test
    fun plus3Arguments() {
        assertEquals(3, """["+", 1, 1, 1]""".readEval())
    }

    @Test
    fun plusDecimal() {
        assertEquals(3, """["+", 1.5, 1.5]""".readEval())
    }

    @Test
    fun plusIntegerDecimal() {
        assertEquals(2.5, """["+", 1, 1.5]""".readEval())
    }

    @Test
    fun minus() {
        assertEquals(3, """["-", 5, 2]""".readEval())
    }

    @Test
    fun times() {
        assertEquals(10, """["*", 5, 2]""".readEval())
    }

    @Test
    fun divIntegers() {
        assertEquals(2, """["/", 5, 2]""".readEval())
    }

    @Test
    fun divDecimals() {
        assertEquals(2.5, """["/", 5.0, 2]""".readEval())
    }
}
