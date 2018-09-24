package com.nathanaelsilverman.jlisp

interface JLispMacro<R> : JLispFunction<R> {

    override fun evaluateParameters() = false
}
