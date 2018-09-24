package com.nathanaelsilverman.jlisp

interface JLispFunction0<out R> : JLispFunction<R> {

    fun call(): R

    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>) = call()
}
