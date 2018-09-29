package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispClosure
import com.nathanaelsilverman.jlisp.JLispFunction
import com.nathanaelsilverman.jlisp.JLispProcessor
import com.nathanaelsilverman.jlisp.isTruthy

internal object If : JLispFunction<Any?> {

    override fun evaluateParameters() = false

    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): Any? {
        require(args.size == 3) {
            "Wrong number of arguments."
        }

        val condition = processor.eval(args[0], closure)
        return processor.eval(
            if (condition.isTruthy()) {
                args[1]
            } else {
                args[2]
            },
            closure
        )
    }
}
