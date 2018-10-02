package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispFunction0Var

/**
 * Had to prepend a 'J' not to conflict with [Array].
 */
internal object JArray : JLispFunction0Var<Any?, List<*>> {
    override fun call(args: List<Any?>) = args
}
