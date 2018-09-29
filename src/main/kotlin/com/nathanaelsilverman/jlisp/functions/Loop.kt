package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispClosure
import com.nathanaelsilverman.jlisp.JLispFunction
import com.nathanaelsilverman.jlisp.JLispMacro
import com.nathanaelsilverman.jlisp.JLispProcessor

/**
 * Note there's no check that recur calls occur as the last action of the loop, but trying to use the result of a
 * recur call inside of a loop would be incorrect and would most likely result in an error.
 */
internal object Loop : JLispMacro<Any?> {
    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): Any? {
        val bindings = args[0] as List<Any?>
        @Suppress("UNCHECKED_CAST")
        val bindingKeys = bindings.filterIndexed { index, _ -> index % 2 == 0 } as List<String>

        val expression: Any? = args[1]

        val recur = "recur" to Recur
        val loopClosure = closure + recur

        // Must contain 2 arguments exactly: the first a flat list of key-value bindings, the second the loop
        // expression.
        var loopArgs = args
        var result: Any?

        while (true) {
            // We use Let here to reuse its binding logic since it behaves just like loop.
            result = Let.call(processor, loopClosure, loopArgs)

            if (result is Recur.Args) {
                // If the result is a recur call then we loop.
                val newBindings = bindingKeys.withIndex()
                    .flatMap { (index, key) ->
                        listOf(key, result.args[index])
                    }
                loopArgs = listOf(newBindings, expression)
            } else {
                // Otherwise we return the result.
                return result
            }
        }
    }
}

private object Recur : JLispFunction<Any?> {

    class Args(val args: List<Any?>)

    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): Any? {
        return Args(args)
    }
}
