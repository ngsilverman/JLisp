package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispFunctionVar
import java.math.BigDecimal

internal class BigDecimalReducer(
    private val function: BigDecimal.(BigDecimal) -> BigDecimal
) : JLispFunctionVar<Number> {
    override fun call(args: List<Any?>): Number {
        require(args.isNotEmpty())

        val result = args
            .asSequence()
            .map {
                require(it is Number) {
                    "Not a number: \"$it\"."
                }
                // We convert the numbers to strings for BigDecimal to use the appropriate precision.
                BigDecimal(it.toString())
            }
            .reduce { acc, value -> acc.function(value) }

        return if (result.scale() > 0) {
            result.toDouble()
        } else {
            result.toLong()
        }
    }
}

internal val plus = BigDecimalReducer { plus(it) }

internal val minus = BigDecimalReducer { minus(it) }

internal val times = BigDecimalReducer { times(it) }

internal val div = BigDecimalReducer { div(it) }

