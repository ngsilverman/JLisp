package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispFunctionVar

/**
 * TODO Add support for default value if not found.
 */
internal object GetIn : JLispFunctionVar<Any?> {
    @Suppress("UNCHECKED_CAST")
    override fun call(args: List<Any?>): Any? {
        val structure: Any? = args[0]

        val keys = args[1] as List<*>

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
