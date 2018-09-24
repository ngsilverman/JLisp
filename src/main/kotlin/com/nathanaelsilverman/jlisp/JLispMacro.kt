package com.nathanaelsilverman.jlisp

internal interface JLispMacro<R> : JLispFunction<R> {

    override fun evaluateParameters() = false
}
