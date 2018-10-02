package com.nathanaelsilverman.jlisp.functions

import com.nathanaelsilverman.jlisp.JLispFunction0Var
import java.math.BigDecimal

internal class BigDecimalReducer(
    private val function: BigDecimal.(BigDecimal) -> BigDecimal
) : JLispFunction0Var<Number, Number> {
    override fun call(args: List<Number>): Number {
        require(args.isNotEmpty())

        val result = args
            .asSequence()
            .map {
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
