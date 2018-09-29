package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispFunctionVar

/**
 * Had to prepend a 'J' not to conflict with [Array].
 */
internal object JArray : JLispFunctionVar<List<*>> {
    override fun call(args: List<Any?>) = args
}
