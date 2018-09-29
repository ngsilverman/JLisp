package com.nathanaelsilverman.jlisp

import org.junit.jupiter.api.BeforeEach

abstract class BaseTest {

    private lateinit var processor: JLispProcessor

    @Suppress("UNCHECKED_CAST")
    protected fun <T> String.readEval(): T = processor.eval(this.read()) as T

    @BeforeEach
    fun beforeEach() {
        processor = JLispProcessor()
    }
}