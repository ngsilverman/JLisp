package com.nathanaelsilverman.jlisp

import org.json.JSONArray
import org.json.JSONObject

internal fun <T> String.read(): T {
    val trimmed = trim()

    val result: Any = when {
        trimmed.startsWith('[') && trimmed.endsWith(']') -> JSONArray(trimmed)
        trimmed.startsWith('{') && trimmed.endsWith('}') -> JSONObject(trimmed)
        trimmed.startsWith('"') && trimmed.endsWith('"') -> trimmed.trim('"')
        trimmed == "true" || trimmed == "false" -> trimmed.toBoolean()
        trimmed == "null" -> JSONObject.NULL
        trimmed.matches("[0-9]+".toRegex()) -> {
            val long = trimmed.toLong()
            if (long <= Int.MAX_VALUE && long >= Int.MIN_VALUE) {
                long.toInt()
            } else {
                long
            }
        }
        trimmed.matches("[0-9.]+".toRegex()) -> {
            val double = trimmed.toDouble()
            if (double <= Float.MAX_VALUE && double >= Float.MIN_VALUE) {
                double.toFloat()
            } else {
                double
            }
        }
        else -> throw IllegalArgumentException()
    }

    @Suppress("UNCHECKED_CAST")
    return result as T
}
