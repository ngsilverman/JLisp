package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispClosure
import com.nathanaelsilverman.jlisp.JLispFunction
import com.nathanaelsilverman.jlisp.JLispProcessor

/**
 * Had to prepend a 'J' not to conflict with [Map].
 */
internal object JMap : JLispFunction<List<*>?> {
    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): List<*>? {
        // We expect the function to take a single parameter, but we can't rely on it implementing [JLispFunction1]
        // because [Fn] returns a generic [JLispFunction].
        val function = args[0] as JLispFunction<Any?>
        val array = args[1] as List<Any?>?
        return array?.map { function.call(processor, closure, listOf(it)) }
    }
}
