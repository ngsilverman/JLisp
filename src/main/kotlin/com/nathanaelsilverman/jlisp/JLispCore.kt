package com.nathanaelsilverman.jlisp

import org.json.JSONArray

internal object Eval : JLispFunction<Any?> {

    override fun evaluateParameters() = false

    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): Any? {
        val expression: Any? = args[0]
        return eval(processor, closure, expression)
    }

    private fun eval(processor: JLispProcessor, closure: JLispClosure, value: Any?): Any? {
        return when (value) {
            is JSONArray -> evalJsonArray(processor, closure, value)
            is String -> evalString(value, closure)
            else -> value
        }
    }

    private fun evalJsonArray(processor: JLispProcessor, closure: JLispClosure, jsonArray: JSONArray): Any? {
        val first = jsonArray[0]

        // Enables the syntactic sugar where [[1, 2, 3]] is equivalent to ["array", 1, 2, 3].
        if (first is JSONArray && jsonArray.length() == 1) {
            return first
        }

        val functionName = first as String
        val function = closure[functionName]

        require(function is JLispFunction<*>) {
            "$functionName is not a function."
        }
        function as JLispFunction<*>

        val arguments = (1 until jsonArray.length())
                .map { index ->
                    jsonArray[index].let {
                        if (function.evaluateParameters()) {
                            eval(processor, closure, it)
                        } else {
                            it
                        }
                    }
                }

        return function.call(processor, closure, arguments)
    }

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
internal object JArray : JLispFunctionVar<JSONArray> {
    override fun call(args: List<Any?>) = JSONArray().apply {
        args.forEach { put(it) }
    }
}

/**
 * Had to prepend a 'J' not to conflict with [Map].
 */
internal object JMap : JLispFunction<JSONArray?> {
    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): JSONArray? {
        // We expect the function to take a single parameter, but we can't rely on it implementing [JLispFunction1]
        // because [Fn] returns a generic [JLispFunction].
        val function = args[0] as JLispFunction<Any?>
        val array = args[1] as JSONArray?
        return if (array == null) {
            null
        } else {
            JSONArray().apply {
                (0 until array.length()).forEach { index ->
                    val mappedValue = function.call(processor, closure, listOf(array[index]))
                    put(mappedValue)
                }
            }
        }
    }
}

internal object Let : JLispMacro<Any?> {
    override fun call(processor: JLispProcessor, closure: JLispClosure, args: List<Any?>): Any? {
        val bindings = args[0] as JSONArray
        require(bindings.length() % 2 == 0)
        val expression: Any? = args[1]

        var letClosure = closure

        (0 until (bindings.length() / 2))
                .map {
                    val keyIndex = it * 2
                    val key = bindings[keyIndex] as String
                    key to bindings[keyIndex + 1]
                }
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
