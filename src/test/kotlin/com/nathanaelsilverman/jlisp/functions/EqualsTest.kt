package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.BaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class EqualsTest : BaseTest() {

    @Test
    fun equalsNoArgs() {
        assertThrows<IllegalArgumentException> {
            """["="]""".readEval()
        }
    }

    @Test
    fun equalsSingleArg() {
        assertEquals(true, """["=", 1]""".readEval())
    }

    @Test
    fun equalsTrue() {
        assertEquals(true, """["=", 1, 1]""".readEval())
    }

    @Test
    fun equalsFalse() {
        assertEquals(false, """["=", 1, 2]""".readEval())
    }

    @Test
    fun equalsNulls() {
        assertEquals(true, """["=", null, null]""".readEval())
    }

    @Test
    fun equalsArrays() {
        assertEquals(true, """["=", [[1, 2, 3]], [[1, 2, 3]]]""".readEval())
    }

    @Test
    fun equalsObjects() {
        assertEquals(true, """["=", {"foo":"bar"}, {"foo":"bar"}]""".readEval())
    }
}
