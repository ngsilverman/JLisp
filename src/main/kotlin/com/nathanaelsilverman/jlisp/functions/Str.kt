package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispClosure
import com.nathanaelsilverman.jlisp.JLispFunction
import com.nathanaelsilverman.jlisp.JLispProcessor

internal object Str : JLispFunction<String> {
    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): String {
        return StringBuilder().apply {
            args.forEach { append(it) }
        }.toString()
    }
}
