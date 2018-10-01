package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispClosure
import com.nathanaelsilverman.jlisp.JLispMacro
import com.nathanaelsilverman.jlisp.JLispProcessor

internal object ThreadFist : JLispMacro<Any?> {
    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): Any? {
        val startingValue: Any? = args[0]
        return args.asSequence()
            .drop(1)
            .map { it as? List<*> ?: listOf(it) }
            .fold(startingValue) { acc, partialForm ->
                val form = partialForm.toMutableList().apply {
                    add(1, acc)
                }
                processor.eval(form)
            }
    }
}
