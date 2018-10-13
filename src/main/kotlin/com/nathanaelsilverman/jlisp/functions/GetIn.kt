package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispClosure
import com.nathanaelsilverman.jlisp.JLispFunction
import com.nathanaelsilverman.jlisp.JLispProcessor

internal object GetIn : JLispFunction<Any?> {
    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): Any? {
        val structure = args[0]
        @Suppress("UNCHECKED_CAST")
        val keys = args[1] as List<Any>
        val defaultValue = if (args.size == 3) args[2] else null
        return keys.fold(structure) { acc, key ->
            when (acc) {
                is List<*> -> {
                    key as Long
                    if (acc.size > key) {
                        acc[key.toInt()]
                    } else {
                        return defaultValue
                    }
                }
                is Map<*, *> -> acc[key] ?: return defaultValue
                else -> {
                    val className = if (acc != null) acc::class.java.simpleName else "null"
                    throw IllegalArgumentException("Could not get value from $className, only arrays and objects are supported. ")
                }
            }
        }
    }
}
