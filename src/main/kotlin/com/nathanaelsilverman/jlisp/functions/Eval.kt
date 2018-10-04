package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.*

internal object Eval : JLispFunction<Any?> {

    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): Any? {
        requireArgs(args, exactCount = 1)

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
        return map.mapValues { (_, value) -> eval(processor, closure, value) }
    }

    private fun evalList(processor: JLispProcessor, closure: JLispClosure, list: List<*>): Any? {
        val first = list[0]

        // Enables the syntactic sugar where [[1, 2, 3]] is equivalent to ["quote", 1, 2, 3].
        if (first is List<*> && list.size == 1) {
            return first
        }

        // The first element of a form is evaluated before attempting to resolve it to a function. This means forms can
        // start with an expression which returns a function or a string referencing a function.
        val firstEvaluated = eval(processor, closure, first)

        val function = when (firstEvaluated) {
            is JLispFunction<*> -> firstEvaluated
            is String -> closure[firstEvaluated].let {
                requireIs<JLispFunction<*>>(it)
                it as JLispFunction<*>
            }
            else -> throw IllegalArgumentException("Forms must begin with a function.")
        }

        val args = list
            .asSequence()
            .drop(1)
            .let { args ->
                if (function.evaluateArguments()) {
                    args.map { eval(processor, closure, it) }
                } else {
                    args
                }
            }
            .toList()

        return function.call(processor, closure, args)
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
