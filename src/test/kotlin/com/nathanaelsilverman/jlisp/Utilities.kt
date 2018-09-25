package com.nathanaelsilverman.jlisp

import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals

internal fun assertJsonArraysEquals(array1: JSONArray, array2: JSONArray) {
    assertEquals(array1.toString(), array2.toString())
}

internal fun assertJsonObjectsEquals(object1: JSONObject, object2: JSONObject) {
    assertEquals(object1.toString(), object2.toString())
}
