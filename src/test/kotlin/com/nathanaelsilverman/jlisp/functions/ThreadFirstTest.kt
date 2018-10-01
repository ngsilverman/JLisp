package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.BaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ThreadFirstTest : BaseTest() {

    @Test
    fun threadFirst() {
        assertEquals(true, """["->", 1, ["+", 1], ["=", 2]]""".readEval())
    }
}
