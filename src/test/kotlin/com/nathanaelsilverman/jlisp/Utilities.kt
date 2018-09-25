package com.nathanaelsilverman.jlisp

import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals

internal fun String.jsonArray() = JSONArray(this)

internal fun assertJsonObjectsEquals(object1: JSONObject, object2: JSONObject) {
    assertEquals(object1.toString(), object2.toString())
}
