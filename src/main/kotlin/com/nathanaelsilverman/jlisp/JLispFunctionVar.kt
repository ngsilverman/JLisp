package com.nathanaelsilverman.jlisp

interface JLispFunctionVar<out R> : JLispFunction<R> {

    fun call(args: List<Any?>): R

    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>) = call(args)
}
