package com.nathanaelsilverman.jlisp

import org.json.JSONArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class JLispCoreTest {

    private lateinit var processor: JLispProcessor

    private fun Any?.eval() = processor.eval(this)

    private fun Any?.eval(closure: JLispClosure) = processor.eval(this, closure)

    @BeforeEach
    fun beforeEach() {
        processor = JLispProcessor()
    }

    @Test
    fun array() {
        assertEquals(listOf(1, 2, 3), ("""["array", 1, 2, 3]""".jsonArray().eval() as JSONArray).toList())
    }

    @Test
    fun evalNull() {
        assertEquals(null, """["eval", null]""".jsonArray().eval())
    }

    @Test
    fun evalString() {
        assertEquals("Hello, world!", """["eval", "Hello, world!"]""".jsonArray().eval())
    }

    @Test
    fun let() {
        assertEquals(7, """["let", ["variable", 7], "%variable"]""".jsonArray().eval())
    }

    @Test
    fun map() {
        assertEquals(listOf(2, 3, 4), ("""["map", ["fn", ["+", "%", 1]], ["array", 1, 2, 3]]""".jsonArray().eval() as JSONArray).toList())
    }

    @Test
    fun plusInt2Parameters() {
        assertEquals(2, """["+", 1, 1]""".jsonArray().eval())
    }

    @Test
    fun plusInt3Parameters() {
        assertEquals(3, """["+", 1, 1, 1]""".jsonArray().eval())
    }
}
