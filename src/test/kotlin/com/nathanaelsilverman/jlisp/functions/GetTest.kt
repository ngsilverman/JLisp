package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.BaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetTest : BaseTest() {

    @Test
    fun getArray() {
        assertEquals(2, """["get", [[1, 2, 3]], 1]""".readEval())
    }

    @Test
    fun getObject() {
        assertEquals(7, """["get", {"key": 7}, "key"]""".readEval())
    }

    @Test
    fun getArrayKeyNotFound() {
        assertEquals(null, """["get", [[1, 2, 3]], 5]""".readEval())
    }

    @Test
    fun getArrayKeyDefaultValue() {
        assertEquals(7, """["get", [[1, 2, 3]], 5, 7]""".readEval())
    }
}
