package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.*

internal object Quote : JLispFunction1<Any?, Any?> {

    override fun evaluateArguments() = false

    override fun call(p1: Any?): Any? {
        return p1
    }
}
