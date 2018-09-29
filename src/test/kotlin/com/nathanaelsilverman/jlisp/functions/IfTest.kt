package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.BaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class IfTest : BaseTest() {

    @Test
    fun ifTrue() {
        assertEquals(1, """["if", true, 1, 2]""".readEval())
    }

    @Test
    fun ifTruthy() {
        assertEquals(1, """["if", [[4, 5, 6]], 1, 2]""".readEval())
    }

    @Test
    fun ifFalseFalse() {
        assertEquals(2, """["if", false, 1, 2]""".readEval())
    }

    @Test
    fun ifFalseNil() {
        assertEquals(2, """["if", null, 1, 2]""".readEval())
    }

    /**
     * Tests that the condition parameter is evaluated.
     */
    @Test
    fun ifEvalCondition() {
        assertEquals(1, """["if", ["=", 1, 1], 1, 2]""".readEval())
    }

    /**
     * Tests that the conditionally chosen parameter is evaluated.
     */
    @Test
    fun ifEvalParam() {
        assertEquals(2, """["if", false, 1, ["+", 1, 1]]""".readEval())
    }
}
