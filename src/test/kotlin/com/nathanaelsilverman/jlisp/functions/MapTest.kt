package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.BaseTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MapTest : BaseTest() {

    @Test
    fun map() {
        assertEquals(listOf(2L, 3L, 4L), """["map", ["fn", ["+", "%", 1]], ["array", 1, 2, 3]]""".readEval())
    }
}
