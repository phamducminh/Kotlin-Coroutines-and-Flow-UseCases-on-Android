package com.pdminh.playground.exceptionhandling

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun main() {
    val scope = CoroutineScope(Job())

    scope.launch {
        try {
            functionThatThrows()
        } catch (e: Exception) {
            println("Caught: $e")
        }
    }

    Thread.sleep(100)
}

private fun functionThatThrows() {
    // some code
    throw RuntimeException()
}