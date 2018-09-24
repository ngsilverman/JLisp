package com.nathanaelsilverman.jlisp

import org.json.JSONArray

internal fun String.jsonArray() = JSONArray(this)
