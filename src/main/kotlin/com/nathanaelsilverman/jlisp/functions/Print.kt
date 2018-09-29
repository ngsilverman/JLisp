package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispFunction1

internal object Print : JLispFunction1<String, Unit> {
    override fun call(p1: String) = print(p1)
}

internal object PrintLn : JLispFunction1<String, Unit> {
    override fun call(p1: String) = println(p1)
}
