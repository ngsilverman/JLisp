package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispClosure
import com.nathanaelsilverman.jlisp.JLispFunction
import com.nathanaelsilverman.jlisp.JLispProcessor

internal object Get : JLispFunction<Any?> {
    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): Any? {
        val structure = args[0]
        val key = args[1]
        val defaultValue = if (args.size == 3) args[2] else null
        return GetIn.call(processor, closure, listOf(structure, listOf(key), defaultValue))
    }
}
