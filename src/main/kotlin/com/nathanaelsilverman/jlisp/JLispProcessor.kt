package com.nathanaelsilverman.jlisp

import org.json.JSONArray
import org.json.JSONObject

class JLispProcessor {

    private val eval = object : JLispFunction1<Any?, Any?> {
        override fun call(p1: Any?): Any? = eval(p1)
    }

    private val coreClosure: JLispClosure = mutableMapOf<String, JLispFunction<*>>().apply {
        put("+", Plus)
        put("array", JArray)
        put("eval", eval)
        put("fn", Fn)
        put("let", Let)
        put("map", JMap)
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
