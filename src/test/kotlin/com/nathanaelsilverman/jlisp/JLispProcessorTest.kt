package com.nathanaelsilverman.jlisp

import org.json.JSONArray
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class JLispProcessorTest {

    private lateinit var processor: JLispProcessor

    private fun String.eval() = processor.eval(JSONArray(this))

    @BeforeEach
    fun beforeEach() {
        processor = JLispProcessor()
    }

    @Test
    fun evalNull() {
        assertEquals(null, processor.eval(null))
    }

    @Test
    fun evalString() {
        assertEquals("Hello, world!", processor.eval("Hello, world!"))
    }

    @Test
    fun evalFunction() {
        assertEquals(Print, processor.eval(Print))
    }

    @Test
    fun evalPlusInt2Parameters() {
        assertEquals(2, """["+", 1, 1]""".eval())
    }

    @Test
    fun evalPlusInt3Parameters() {
        assertEquals(3, """["+", 1, 1, 1]""".eval())
    }

    /**
     * The return value of the let isn't using '%'s, so it's returning the actual string "variable" rather than the
     * value of the "variable" variable.
     */
    @Test
    fun evalLetNoPercent() {
        assertEquals("variable", """["let", ["variable", 7], "variable"]""".eval())
    }

    @Test
    fun evalLet() {
        assertEquals(7, """["let", ["variable", 7], "%variable%"]""".eval())
    }

    /**
     * Escaping the first '%' makes it possible to return a variable name as a string.
     */
    @Test
    fun evalLetPercentString() {
        assertEquals("%variable%", """["let", ["variable", 7], "/%variable%"]""".eval())
    }

    @Test
    fun evalLetBackPercentString() {
        assertEquals("/%variable%", """["let", ["variable", 7], "//%variable%"]""".eval())
    }

    @Test
    fun evalLetSinglePercent() {
        assertEquals("%", """["let", ["variable", 7], "%"]""".eval())
    }

    @Test
    fun evalLetDoublePercent() {
        assertEquals("%%", """["let", ["variable", 7], "%%"]""".eval())
    }
}
