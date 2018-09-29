package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispClosure
import com.nathanaelsilverman.jlisp.JLispFunction
import com.nathanaelsilverman.jlisp.JLispProcessor

internal object Eval : JLispFunction<Any?> {

    override fun evaluateArguments() = false

    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): Any? {
        val expression: Any? = args[0]
        return eval(processor, closure, expression)
    }

    private fun eval(processor: JLispProcessor, closure: JLispClosure, value: Any?): Any? {
        return when (value) {
            is List<*> -> evalList(processor, closure, value)
            is Map<*, *> -> evalMap(processor, closure, value)
            is String -> evalString(value, closure)
            else -> value
        }
    }

    private fun evalMap(processor: JLispProcessor, closure: JLispClosure, map: Map<*, *>): Map<*, *> {
        return map.mapValues { (_, value) ->
            eval(
                processor,
                closure,
                value
            )
        }
    }

    private fun evalList(processor: JLispProcessor, closure: JLispClosure, list: List<*>): Any? {
        val first = list[0]

        // Enables the syntactic sugar where [[1, 2, 3]] is equivalent to ["quote", 1, 2, 3].
        if (first is List<*> && list.size == 1) {
            return first
        }

        val functionName = first as String
        val function = closure[functionName]

        require(function is JLispFunction<*>) {
            "$functionName is not a function."
        }
        function as JLispFunction<*>

        val arguments = list
            .asSequence()
            .drop(1)
            .map {
                if (function.evaluateArguments()) {
                    eval(processor, closure, it)
                } else {
                    it
                }
            }
            .toList()

        return function.call(processor, closure, arguments)
    }

    /**
     * Strings need to be evaluated because they can be variables.
     */
    private fun evalString(string: String, closure: JLispClosure): Any? {
        return when {
            string.startsWith('%') -> {
                val variableName = if (string == "%") "%1" else string
                closure[variableName.drop(1)].also {
                    requireNotNull(it) {
                        "Variable \"$variableName\" was not set."
                    }
                }
            }
            string.contains("^/+%".toRegex()) -> {
                string.drop(1)
            }
            else -> string
        }
    }
}
