package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispFunction0Var
import com.nathanaelsilverman.jlisp.requireArgs

internal object Equals : JLispFunction0Var<Any?, Boolean> {
    override fun call(args: List<Any?>): Boolean {
        requireArgs(args, minCount = 1)

        val first = args.first()
        args.drop(1)
            .forEach {
                if (first != it) {
                    return false
                }
            }

        return true
    }
}
