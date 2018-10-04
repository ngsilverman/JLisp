@file:Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")

package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispClosure
import com.nathanaelsilverman.jlisp.JLispFunction
import com.nathanaelsilverman.jlisp.JLispFunction1
import com.nathanaelsilverman.jlisp.JLispProcessor

internal object Fn : JLispFunction1<Any?, JLispFunction<Any?>> {

    override fun evaluateArguments() = false

    override fun call(expression: Any?): JLispFunction<Any?> {

        return object : JLispFunction<Any?> {

            override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): Any? {

                val functionClosure = closure.plus(
                    args.mapIndexed { index, arg ->
                        // Arguments are bound to indexed variables starting with 1.
                        (index + 1).toString() to arg
                    }
                )

                return processor.eval(expression, functionClosure)
            }
        }
    }
}
