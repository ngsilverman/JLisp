package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.BaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EvalTest : BaseTest() {

    @Test
    fun evalNoArgs() {
        assertThrows<IllegalArgumentException> {
            """["eval"]""".readEval()
        }
    }

    @Test
    fun evalMultipleArgs() {
        assertThrows<IllegalArgumentException> {
            """["eval", 1, 2]""".readEval()
        }
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
    fun evalArray() {
        assertEquals(2, """["eval", [["+", 1, 1]]]""".readEval())
    }

    /**
     * Tests that the parameters are evaluated before running eval.
     */
    @Test
    fun evalEvaluateParameters() {
        assertEquals(2, """["eval", ["if", true, [["+", 1, 1]], 0]]""".readEval())
    }

    @Test
    fun evalObject() {
        assertEquals(mapOf("key" to 2L), """["eval", {"key": ["+", 1, 1]}]""".readEval())
    }

    /**
     * Tests that forms with a function object as their first element and no arguments are correctly evaluated.
     */
    @Test
    fun evalFnFormNoArgs() {
        assertEquals(null, """["eval", ["array", ["fn", null]]]""".readEval())
    }

    /**
     * Tests that forms with a function object as their first element are correctly evaluated.
     */
    @Test
    fun evalFirstFunctionObject() {
        assertEquals(8, """["eval", [[["fn", ["+", "%1", 1]], 7]]]""".readEval())
    }

    /**
     * Tests that forms with an expression returning a string referencing a function as their first element are
     * correctly evaluated.
     */
    @Test
    fun evalFirstFunctionReferenceExpression() {
        assertEquals(7, """["eval", [[["str", "l", "e", "t"], ["x", 7], "%x"]]]""".readEval())
    }
}
