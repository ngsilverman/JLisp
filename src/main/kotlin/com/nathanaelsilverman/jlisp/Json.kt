package com.nathanaelsilverman.jlisp

import org.json.JSONArray
import org.json.JSONObject

internal fun JSONArray.toList(): List<*> {
    return (0 until length()).map { index -> get(index) }
}

internal fun JSONObject.copy() = JSONObject(toString())
