package com.nathanaelsilverman.jlisp

interface JLispFunction1<in P1, out R> : JLispFunction<R> {

    fun call(p1: P1): R

    @Suppress("UNCHECKED_CAST")
    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>) = call(args[0] as P1)
}
