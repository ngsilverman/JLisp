package com.nathanaelsilverman.jlisp

import org.json.JSONArray

internal fun JSONArray.toList(): List<*> {
    return (0 until length()).map { index -> get(index) }
}
