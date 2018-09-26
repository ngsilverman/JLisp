package com.nathanaelsilverman.jlisp

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class JLispCoreTest {

    private lateinit var processor: JLispProcessor

    @Suppress("UNCHECKED_CAST")
    private fun <T> String.readEval(): T = processor.eval(this.read()) as T

    @BeforeEach
    fun beforeEach() {
        processor = JLispProcessor()
    }

    @Test
    fun array() {
        assertEquals(listOf(1L, 2L, 3L), """["array", 1, 2, 3]""".readEval())
    }

    @Test
    fun arraySyntacticSugar() {
        assertEquals(listOf(1L, 2L, 3L), """[[1, 2, 3]]""".readEval())
    }

    @Test
    fun equals() {
        assertEquals(true, """["=", 1, 1]""".readEval())
    }

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

    @Test
    fun let() {
        assertEquals(7, """["let", ["variable", 7], "%variable"]""".readEval())
    }

    @Test
    fun map() {
        assertEquals(listOf(2L, 3L, 4L), """["map", ["fn", ["+", "%", 1]], ["array", 1, 2, 3]]""".readEval())
    }

    @Test
    fun plus2Parameters() {
        assertEquals(2, """["+", 1, 1]""".readEval())
    }

    @Test
    fun plus3Parameters() {
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
