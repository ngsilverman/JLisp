package com.nathanaelsilverman.jlisp

internal interface JLispFunction<out R> {

    /**
     * For a given implementation, should always return the same value ([true] or [false]).
     */
    fun evaluateParameters() = true

    fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): R
}
