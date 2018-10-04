package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.BaseTest
import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class FnTest : BaseTest() {

    @Test
    fun fnNoArgs() {
        assertThrows<IllegalArgumentException> {
            """["fn"]""".readEval()
        }
    }

    @Test
    fun fnMultipleArgs() {
        assertThrows<IllegalArgumentException> {
            """["fn", 1, 2]""".readEval()
        }
    }

    @Test
    fun fnNoParams() {
        assertEquals(7, """["eval", ["array", ["fn", 7]]]""".readEval())
    }

    @Test
    fun fnSingleParam() {
        assertEquals(7, """[["fn", "%1"], 7]""".readEval())
    }

    @Test
    fun fnMultiParams() {
        assertEquals(15, """[["fn", ["+", "%1", "%2"]], 7, 8]""".readEval())
    }
}
