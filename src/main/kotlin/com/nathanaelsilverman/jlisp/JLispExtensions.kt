package com.nathanaelsilverman.jlisp

import org.json.simple.parser.JSONParser

/**
 * Converts a [String] into an object.
 */
@Suppress("UNCHECKED_CAST")
internal fun <T> String.read(): T = JSONParser().parse(this) as T

/**
 * Like Clojure, `false` and `null` are the only falsey values.
 */
internal fun Any?.isTruthy(): Boolean = this != false && this != null

/**
 * Like Clojure, `false` and `null` are the only falsey values.
 */
internal fun Any?.isFalsy(): Boolean = !isTruthy()
