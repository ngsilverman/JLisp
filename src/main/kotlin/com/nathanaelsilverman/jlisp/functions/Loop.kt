package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispClosure
import com.nathanaelsilverman.jlisp.JLispFunction
import com.nathanaelsilverman.jlisp.JLispMacro
import com.nathanaelsilverman.jlisp.JLispProcessor

internal object Loop : JLispMacro<Any?> {
    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): Any? {
        val bindings = args[0] as List<Any?>
        @Suppress("UNCHECKED_CAST")
        val bindingKeys = bindings.filterIndexed { index, _ -> index % 2 == 0 } as List<String>

        val expression: Any? = args[1]

        val recur = "recur" to Recur(bindingKeys, expression)

        return Let.call(processor, closure + recur, args)
    }
}

private class Recur(val bindingKeys: List<String>, val expression: Any?) : JLispFunction<Any?> {
    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): Any? {
        val newBindings = bindingKeys.withIndex()
            .flatMap { (index, key) ->
                listOf(key, args[index])
            }
        return Loop.call(processor, closure, listOf(newBindings, expression))
    }
}
