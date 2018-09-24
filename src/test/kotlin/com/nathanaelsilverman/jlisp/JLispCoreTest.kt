package com.nathanaelsilverman.jlisp

import org.json.JSONArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class JLispCoreTest {

    private lateinit var processor: JLispProcessor

    private fun Any?.eval() = processor.eval(this)

    private fun Any?.eval(closure: JLispClosure) = processor.eval(this, closure)

    private fun String.jsonArray() = JSONArray(this)

    @BeforeEach
    fun beforeEach() {
        processor = JLispProcessor()
    }

    @Test
    fun evalPlusInt2Parameters() {
        assertEquals(2, """["+", 1, 1]""".jsonArray().eval())
    }

    @Test
    fun evalPlusInt3Parameters() {
        assertEquals(3, """["+", 1, 1, 1]""".jsonArray().eval())
    }

    @Test
    fun evalLet() {
        assertEquals(7, """["let", ["variable", 7], "%variable%"]""".jsonArray().eval())
    }
}
