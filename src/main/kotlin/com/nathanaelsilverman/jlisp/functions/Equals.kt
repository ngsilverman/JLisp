package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispFunctionVar

internal object Equals : JLispFunctionVar<Boolean> {
    override fun call(args: List<Any?>): Boolean {
        require(args.size >= 2)

        var first = args.first()
        args.drop(1)
            .forEach {
                if (first != it) {
                    return false
                }
                first = it
            }

        return true
    }
}
