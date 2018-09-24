package com.nathanaelsilverman.jlisp

internal interface JLispFunction<out R> {

    fun evaluateParameters() = true

    fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): R
}
