package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.BaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EqualsTest : BaseTest() {

    @Test
    fun equals() {
        assertEquals(true, """["=", 1, 1]""".readEval())
    }
}
