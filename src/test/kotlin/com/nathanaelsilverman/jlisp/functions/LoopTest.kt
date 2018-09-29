package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.BaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LoopTest : BaseTest() {

    @Test
    fun loopNoLoop() {
        assertEquals(7, """["loop", ["variable", 7], "%variable"]""".readEval())
    }

    @Test
    fun loop() {
        assertEquals(
            7,
            """
                ["loop", ["variable", 0],
                  ["if", ["=", "%variable", 7],
                    "%variable",
                    ["recur", ["+", "%variable", 1]]]]
                """.readEval()
        )
    }
}
