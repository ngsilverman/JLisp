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

    /**
     * Without loop's tail recursion optimization this would result in a StackOverflow exception.
     */
    @Test
    fun tailRecursion() {
        assertEquals(
            "a".repeat(10000),
            """
                ["loop", ["result", "a",
                          "continue", 10000],
                  ["if", ["=", "%continue", 1],
                    "%result",
                    ["recur", ["str", "%result", "a"], ["-", "%continue", 1]]]]
                """.readEval()
        )
    }
}
