package com.nathanaelsilverman.jlisp

interface JLispFunction3<in P1, in P2, in P3, out R> : JLispFunction<R> {

    fun call(p1: P1, p2: P2, p3: P3): R

    @Suppress("UNCHECKED_CAST")
    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>) =
        call(args[0] as P1, args[1] as P2, args[2] as P3)
}
