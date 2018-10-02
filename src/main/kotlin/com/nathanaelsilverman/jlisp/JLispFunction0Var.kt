package com.nathanaelsilverman.jlisp

interface JLispFunction0Var<in V, out R> : JLispFunction<R> {

    fun call(args: List<V>): R

    @Suppress("UNCHECKED_CAST")
    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>) = call(args as List<V>)
}
