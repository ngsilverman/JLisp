package com.nathanaelsilverman.jlisp

class JLispProcessor(
    private val eval: JLispFunction<Any?> = Eval,
    private val coreClosure: JLispClosure = mapOf<String, JLispFunction<*>>(
        "+" to Plus,
        "array" to JArray,
        "eval" to Eval,
        "fn" to Fn,
        "let" to Let,
        "map" to JMap,
        "print" to Print,
        "println" to PrintLn
    )
) {

    fun eval(value: Any?): Any? = eval(value, coreClosure)

    internal fun eval(value: Any?, closure: JLispClosure): Any? {
        return eval.call(this, closure, listOf(value))
    }
}
