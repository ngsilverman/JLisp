package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.BaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class QuoteTest : BaseTest() {

    @Test
    fun quote() {
        assertEquals(listOf(1L, 2L, 3L), """["quote", [1, 2, 3]]""".readEval())
    }

    /**
     * Tests that the content of the array is not evaluated.
     */
    @Test
    fun quoteUnevalArray() {
        assertEquals(listOf(listOf("+", 1L, 1L), 3L, 4L), """["quote", [["+", 1, 1], 3, 4]]""".readEval())
    }

    /**
     * Tests that the content of the object is not evaluated.
     */
    @Test
    fun quoteUnevalObject() {
        assertEquals(mapOf("key" to listOf("+", 1L, 1L)), """["quote", {"key": ["+", 1, 1]}]""".readEval())
    }

    @Test
    fun quoteSyntacticSugar() {
        assertEquals(listOf(1L, 2L, 3L), """[[1, 2, 3]]""".readEval())
    }

    /**
     * Tests that the content of the array is not evaluated.
     */
    @Test
    fun quoteSyntacticSugarUneval() {
        assertEquals(listOf(listOf("+", 1L, 1L), 3L, 4L), """[[["+", 1, 1], 3, 4]]""".readEval())
    }
}
