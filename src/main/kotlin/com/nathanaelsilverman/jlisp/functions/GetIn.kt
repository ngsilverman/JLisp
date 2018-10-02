package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispFunction2

/**
 * TODO Add support for default value if not found.
 */
internal object GetIn : JLispFunction2<Any?, List<Any>, Any?> {
    @Suppress("UNCHECKED_CAST", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun call(structure: Any?, keys: List<Any>): Any? {
        return when (structure) {
            is List<*> -> {
                keys as List<Int>
                keys.fold(structure as Any?) { acc, value ->
                    (acc as List<Any?>)[value]
                }
            }
            is Map<*, *> -> {
                keys as List<String>
                keys.fold(structure as Any?) { acc, value ->
                    (acc as Map<String, Any?>)[value]
                }
            }
            else -> throw IllegalArgumentException("get-in's first argument must be a list or a map.")
        }
    }
}
