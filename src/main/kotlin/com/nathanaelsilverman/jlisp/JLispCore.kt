package com.nathanaelsilverman.jlisp

import org.json.simple.JSONArray

internal object Eval : JLispFunction<Any?> {

    override fun evaluateParameters() = false

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
        return map.mapValues { (_, value) -> eval(processor, closure, value) }
    }

    private fun evalList(processor: JLispProcessor, closure: JLispClosure, list: List<*>): Any? {
        val first = list[0]

        // Enables the syntactic sugar where [[1, 2, 3]] is equivalent to ["array", 1, 2, 3].
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
                if (function.evaluateParameters()) {
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

internal object Fn : JLispFunction1<Any?, JLispFunction<Any?>> {

    override fun evaluateParameters() = false

    override fun call(p1: Any?): JLispFunction<Any?> {
        return object : JLispFunction<Any?> {
            override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): Any? {
                var functionClosure = closure
                args.forEachIndexed { index, arg ->
                    // Parameters are bound to indexed variables starting with 1.
                    val binding = (index + 1).toString() to arg
                    functionClosure += binding
                }
                return processor.eval(p1, functionClosure)
            }
        }
    }
}

/**
 * Had to prepend a 'J' not to conflict with [Array].
 */
internal object JArray : JLispFunctionVar<List<*>> {
    override fun call(args: List<Any?>) = args
}

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

internal object Let : JLispMacro<Any?> {
    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): Any? {
        val bindings = args[0] as List<Any?>
        require(bindings.size % 2 == 0)
        val expression: Any? = args[1]

        var letClosure = closure

        bindings.asSequence()
            .chunked(2)
            .map { it[0] as String to it[1] }
            .forEach { (key, value) ->
                val binding = key to processor.eval(value, letClosure)
                letClosure += binding
            }

        return processor.eval(expression, letClosure)
    }
}

internal object Plus : JLispFunctionVar<Any> {
    override fun call(args: List<Any?>): Any {
        require(args.isNotEmpty())

        val first = args[0]!!

        if (args.size == 1) return first

        val rest = call(args.drop(1))

        return when (first) {
            is Byte -> when (rest) {
                is Byte -> first + rest
                is Short -> first + rest
                is Int -> first + rest
                is Long -> first + rest
                is Float -> first + rest
                is Double -> first + rest
                else -> error("Not a number.")
            }
            is Short -> when (rest) {
                is Byte -> first + rest
                is Short -> first + rest
                is Int -> first + rest
                is Long -> first + rest
                is Float -> first + rest
                is Double -> first + rest
                else -> error("Not a number.")
            }
            is Int -> when (rest) {
                is Byte -> first + rest
                is Short -> first + rest
                is Int -> first + rest
                is Long -> first + rest
                is Float -> first + rest
                is Double -> first + rest
                else -> error("Not a number.")
            }
            is Long -> when (rest) {
                is Byte -> first + rest
                is Short -> first + rest
                is Int -> first + rest
                is Long -> first + rest
                is Float -> first + rest
                is Double -> first + rest
                else -> error("Not a number.")
            }
            is Float -> when (rest) {
                is Byte -> first + rest
                is Short -> first + rest
                is Int -> first + rest
                is Long -> first + rest
                is Float -> first + rest
                is Double -> first + rest
                else -> error("Not a number.")
            }
            is Double -> when (rest) {
                is Byte -> first + rest
                is Short -> first + rest
                is Int -> first + rest
                is Long -> first + rest
                is Float -> first + rest
                is Double -> first + rest
                else -> error("Not a number.")
            }
            else -> error("Not a number.")
        }
    }
}

internal object Print : JLispFunction1<String, Unit> {
    override fun call(p1: String) = print(p1)
}

internal object PrintLn : JLispFunction1<String, Unit> {
    override fun call(p1: String) = println(p1)
}
