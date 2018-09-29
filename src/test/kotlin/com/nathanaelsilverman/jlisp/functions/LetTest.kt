package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.BaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LetTest : BaseTest() {

    @Test
    fun let() {
        assertEquals(7, """["let", ["variable", 7], "%variable"]""".readEval())
    }
}
