package com.nathanaelsilverman.jlisp

import org.json.JSONArray

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
