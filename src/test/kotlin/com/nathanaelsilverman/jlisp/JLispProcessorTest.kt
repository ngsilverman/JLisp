package com.nathanaelsilverman.jlisp

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class JLispProcessorTest {

    private lateinit var processor: JLispProcessor

    private fun Any?.eval() = processor.eval(this)

    private fun Any?.eval(closure: JLispClosure) = processor.eval(this, closure)

    @BeforeEach
    fun beforeEach() {
        processor = JLispProcessor()
    }

    @Test
    fun evalNull() {
        assertEquals(null, null.eval())
    }

    @Test
    fun evalString() {
        assertEquals("Hello, world!", "Hello, world!".eval())
    }

    @Test
    fun evalFunction() {
        assertEquals(Print, Print.eval())
    }

    @Test
    fun variable() {
        assertEquals(7, "%variable%".eval(mapOf("variable" to 7)))
    }

    /**
     * Escaping the first '%' makes it possible to return a variable name as a string.
     */
    @Test
    fun stringPercent() {
        assertEquals("%variable%", "/%variable%".eval())
    }

    @Test
    fun stringBackPercent() {
        assertEquals("/%variable%", "//%variable%".eval())
    }

    @Test
    fun stringSinglePercent() {
        assertEquals("%", "%".eval())
    }

    @Test
    fun stringDoublePercent() {
        assertEquals("%%", "%%".eval())
    }
}
