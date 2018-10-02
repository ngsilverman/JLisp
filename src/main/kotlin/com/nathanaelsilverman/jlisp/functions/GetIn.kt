package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispFunction2

/**
 * TODO Add support for default value if not found.
 */
internal object GetIn : JLispFunction2<Any?, List<Any>, Any?> {
    @Suppress("UNCHECKED_CAST", "PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun call(structure: Any?, keys: List<Any>): Any? {
        return keys.fold(structure) { acc, key ->
            when (acc) {
                is List<*> -> acc[(key as Long).toInt()]
                is Map<*, *> -> acc[key]
                else -> {
                    val className = if (acc != null) acc::class.java.simpleName else "null"
                    throw IllegalArgumentException("Could not get value from $className, only arrays and objects are supported. ")
                }
            }
        }
    }
}
