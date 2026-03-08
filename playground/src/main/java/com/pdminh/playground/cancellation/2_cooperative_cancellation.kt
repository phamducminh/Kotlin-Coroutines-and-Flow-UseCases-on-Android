package com.pdminh.playground.cancellation

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield

fun main() = runBlocking {

    val job = launch(Dispatchers.Default) {

        repeat(10) { index ->
//            ensureActive()
//            yield()
            if (isActive) {
                println("operation number $index")
                Thread.sleep(100)
            } else {
                withContext(NonCancellable) {
                    delay(100)
                    println("Cleaning up ...")
                    throw CancellationException()
                }
            }
        }
    }

    delay(250)
    println("Cancelling Coroutine")
    job.cancel()
}