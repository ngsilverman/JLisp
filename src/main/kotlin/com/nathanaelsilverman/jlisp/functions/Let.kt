package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispClosure
import com.nathanaelsilverman.jlisp.JLispMacro
import com.nathanaelsilverman.jlisp.JLispProcessor

internal object Let : JLispMacro<Any?> {
    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): Any? {
        val bindings = args[0] as List<Any?>
        require(bindings.size % 2 == 0)
        val expression: Any? = args[1]

        var letClosure = closure

        bindings.asSequence()
            .chunked(2)
            .map { it[0] as String to it[1] }
            .forEach { (key, value) ->
                val binding = key to processor.eval(value, letClosure)
                letClosure += binding
            }

        return processor.eval(expression, letClosure)
    }
}
