package com.nathanaelsilverman.jlisp

fun requireArgs(
    args: List<Any?>,
    exactCount: Int? = null,
    minCount: Int? = null,
    maxCount: Int? = null
) {
    val count = args.size

    require(exactCount == null || count == exactCount) {
        "Invalid argument count. Expected exactly $exactCount but got $count."
    }

    require(minCount == null || count >= minCount) {
        "Invalid argument count. Expected at least $minCount but got $count."
    }

    require(maxCount == null || count <= maxCount) {
        "Invalid argument count. Expected at most $maxCount but got $count."
    }
}

inline fun <reified T> requireIs(any: Any?) {
    require(any is T) {
        "Invalid type. Expected $any to be of type ${T::class.java.canonicalName} but was ${any!!::class.java.canonicalName}"
    }
}
