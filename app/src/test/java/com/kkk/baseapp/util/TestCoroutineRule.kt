package com.kkk.baseapp.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

internal class TestCoroutineRule: TestRule {

    @ExperimentalCoroutinesApi
    private val testCoroutineDispatcher = UnconfinedTestDispatcher()
    @ExperimentalCoroutinesApi
    val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    @ExperimentalCoroutinesApi
    override fun apply(base: Statement, description: Description): Statement = object: Statement(){
        override fun evaluate() {
            Dispatchers.setMain(testCoroutineDispatcher)
            base.evaluate()
            Dispatchers.resetMain()
            testCoroutineScope.cleanupTestCoroutines()
        }
    }

    @ExperimentalCoroutinesApi
    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) = testCoroutineScope.runBlockingTest { block() }

}