package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.BaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetInTest : BaseTest() {

    @Test
    fun getInArraySingleKey() {
        assertEquals(2, """["get-in", [[1, 2, 3]], [[1]]]""".readEval())
    }

    @Test
    fun getInArrayMultipleKeys() {
        assertEquals(6, """["get-in", [[[1, 2, 3], [4, 5, 6]]], [[1, 2]]]""".readEval())
    }

    @Test
    fun getInObjectSingleKey() {
        assertEquals(7, """["get-in", {"key": 7}, [["key"]]]""".readEval())
    }

    @Test
    fun getInObjectMultipleKeys() {
        assertEquals(7, """["get-in", {"first": {"second": 7}}, [["first", "second"]]]""".readEval())
    }

    @Test
    fun getInMix() {
        assertEquals(3, """["get-in", {"first": [[1, 2, 3]]}, [["first", 2]]]""".readEval())
    }

    @Test
    fun getInArrayKeyNotFound() {
        assertEquals(null, """["get-in", [[1, 2, 3]], [[5]]]""".readEval())
    }

    @Test
    fun getInArrayKeyDefaultValue() {
        assertEquals(7, """["get-in", [[1, 2, 3]], [[5]], 7]""".readEval())
    }
}
