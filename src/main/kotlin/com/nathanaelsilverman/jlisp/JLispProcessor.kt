package com.nathanaelsilverman.jlisp

import org.json.JSONArray

class JLispProcessor {

    private val eval = object : JLispFunction1<Any?, Any?> {
        override fun call(p1: Any?): Any? = eval(p1)
    }

    private val coreClosure: JLispClosure = mutableMapOf<String, JLispFunction<*>>().apply {
        put("+", Plus)
        put("eval", eval)
        put("let", Let)
        put("print", Print)
        put("println", PrintLn)
    }

    fun eval(value: Any?): Any? = eval(value, coreClosure)

    internal fun eval(value: Any?, closure: JLispClosure): Any? {
        return when (value) {
            is JSONArray -> evalJsonArray(value, closure)
            is String -> evalString(value, closure)
            else -> value
        }
    }

    private fun evalJsonArray(jsonArray: JSONArray, closure: JLispClosure): Any? {
        val functionName = jsonArray.getString(0)
        val function = closure[functionName]

        require(function is JLispFunction<*>) {
            "$functionName is not a function."
        }
        function as JLispFunction<*>

        val arguments = (1 until jsonArray.length())
                .map { index ->
                    jsonArray[index].let {
                        if (function.evaluateParameters()) {
                            eval(it, closure)
                        } else {
                            it
                        }
                    }
                }

        return function.call(this, closure, arguments)
    }

    private fun evalString(string: String, closure: JLispClosure): Any? {
        return when {
            string.length > 2 && string.startsWith('%') && string.endsWith('%') -> {
                closure[string.drop(1).dropLast(1)].also {
                    requireNotNull(it) {
                        "Variable \"$string\" was not set."
                    }
                }
            }
            string.length > 2 && string.contains("^/+%".toRegex()) && string.endsWith('%') -> {
                string.drop(1)
            }
            else -> string
        }
    }
}
