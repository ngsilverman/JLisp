package com.nathanaelsilverman.jlisp

import org.json.simple.parser.JSONParser

@Suppress("UNCHECKED_CAST")
internal fun <T> String.read(): T = JSONParser().parse(this) as T
