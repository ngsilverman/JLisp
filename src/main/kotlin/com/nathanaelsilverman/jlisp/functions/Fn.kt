package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispClosure
import com.nathanaelsilverman.jlisp.JLispFunction
import com.nathanaelsilverman.jlisp.JLispFunction1
import com.nathanaelsilverman.jlisp.JLispProcessor

internal object Fn :
    JLispFunction1<Any?, JLispFunction<Any?>> {

    override fun evaluateParameters() = false

    override fun call(p1: Any?): JLispFunction<Any?> {
        return object : JLispFunction<Any?> {
            override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): Any? {
                var functionClosure = closure
                args.forEachIndexed { index, arg ->
                    // Parameters are bound to indexed variables starting with 1.
                    val binding = (index + 1).toString() to arg
                    functionClosure += binding
                }
                return processor.eval(p1, functionClosure)
            }
        }
    }
}
