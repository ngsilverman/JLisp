package com.nathanaelsilverman.jlisp

interface JLispMacro<R> : JLispFunction<R> {

    override fun evaluateArguments() = false
}
