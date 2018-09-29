package com.nathanaelsilverman.jlisp

import com.nathanaelsilverman.jlisp.functions.*

class JLispProcessor(
    private val eval: JLispFunction<Any?> = Eval,
    private val coreClosure: JLispClosure = mapOf<String, JLispFunction<*>>(
        "=" to Equals,
        "+" to plus,
        "-" to minus,
        "*" to times,
        "/" to div,
        "array" to JArray,
        "eval" to Eval,
        "fn" to Fn,
        "let" to Let,
        "if" to If,
        "loop" to Loop,
        "map" to JMap,
        "print" to Print,
        "println" to PrintLn,
        "quote" to Quote,
        "str" to Str
    )
) {

    fun eval(value: Any?): Any? = eval(value, coreClosure)

    internal fun eval(value: Any?, closure: JLispClosure): Any? {
        return eval.call(this, closure, listOf(value))
    }

    fun read(string: String): Any? = string.read()
}
