package com.nathanaelsilverman.jlisp

import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class JLispExtensionsTest {

    @Test
    fun readArray() {
        assertJsonArraysEquals(JSONArray(listOf(1, 2, 3)), """[1, 2, 3]""".read())
    }

    @Test
    fun readObject() {
        assertJsonObjectsEquals(JSONObject(mapOf("key" to 2)), """{"key": 2}""".read())
    }

    @Test
    fun readString() {
        assertEquals("Yellow brick road", "\"Yellow brick road\"".read())
    }

    @Test
    fun readTrue() {
        assertEquals(true, "true".read())
    }

    @Test
    fun readFalse() {
        assertEquals(false, "false".read())
    }

    @Test
    fun readNull() {
        assertEquals(JSONObject.NULL, "null".read())
    }

    /**
     * Tests that whitespace is ignored.
     */
    @Test
    fun readTrim() {
        assertEquals(JSONObject.NULL, " null  ".read())
    }

    @Test
    fun readInt() {
        assertEquals(7, "7".read())
    }

    @Test
    fun readLong() {
        assertEquals(21474836477, "21474836477".read())
    }

    @Test
    fun readFloat() {
        assertEquals(5.1f, "5.1".read())
    }

    @Test
    fun readDouble() {
        assertEquals(21474836477.5, "21474836477.5".read(), 10.0)
    }

    @Test
    fun readInvalid() {
        assertThrows<IllegalArgumentException> {
            "invalid".read<Nothing>()
        }
    }
}
