package com.pdminh.playground.structuredconcurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val scope = CoroutineScope(Dispatchers.Default)
    val parentCoroutineJob = scope.launch {
        launch {
            delay(1000)
            println("Child Coroutine 1 has completed")
        }
        launch {
            delay(1000)
            println("Child Coroutine 2 has completed")
        }
    }

    parentCoroutineJob.join()

    println("Parent coroutine has completed")
}