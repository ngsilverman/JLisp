@file:Suppress("UNCHECKED_CAST")

package com.nathanaelsilverman.jlisp

interface JLispFunction1<in P1, out R> : JLispFunction<R> {

    fun call(p1: P1): R

    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): R {
        requireArgs(args, exactCount = 1)
        return call(args[0] as P1)
    }
}
