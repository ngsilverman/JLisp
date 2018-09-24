package com.nathanaelsilverman.jlisp

class JLispProcessor {

    private val coreClosure: JLispClosure = mutableMapOf<String, JLispFunction<*>>().apply {
        put("+", Plus)
        put("array", JArray)
        put("eval", Eval)
        put("fn", Fn)
        put("let", Let)
        put("map", JMap)
        put("print", Print)
        put("println", PrintLn)
    }

    fun eval(value: Any?): Any? = eval(value, coreClosure)

    internal fun eval(value: Any?, closure: JLispClosure): Any? {
        return Eval.call(this, closure, listOf(value))
    }
}
