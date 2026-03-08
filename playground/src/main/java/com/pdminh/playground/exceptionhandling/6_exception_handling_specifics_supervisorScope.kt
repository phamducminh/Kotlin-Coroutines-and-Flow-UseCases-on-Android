package com.pdminh.playground.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

fun main() = runBlocking<Unit> {

    // CRASH because the exception propagates up to the supervisorScope
    // and because we haven't installed a CoroutineExceptionHandler,
    // the app will crash
    try {
        supervisorScope {
            launch {
                throw RuntimeException()
            }
        }
    } catch (e: Exception) {
        println("Caught $e")
    }

    // No Crash. The exception is encapsulated in the Deferred object of the async
    // coroutine, and the exception will only be thrown when we call await on the deferred
    try {
        supervisorScope {
            async {
                throw RuntimeException()
            }.await()
        }
    } catch (e: Exception) {
        println("Caught $e")
    }

    // CRASH
    try {
        supervisorScope {
            val deferred = async {
                throw RuntimeException()
            }

            launch {
                deferred.await()
            }
        }
    } catch (e: Exception) {
        println("Caught $e")
    }

    // No Crash. The exception will be thrown by supervisorScope
    // Therefore, we can handle it with the outer try-catch
    try {
        supervisorScope {
            throw RuntimeException()
        }
    } catch (e: Exception) {
        println("Caught $e")
    }
}

fun anotherMain() {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught $throwable in CoroutineExceptionHandler")
    }

    val scope = CoroutineScope(Job())

    // No Crash
    scope.launch {
        try {
            supervisorScope {
                launch(exceptionHandler) {
                    throw RuntimeException()
                }
            }
        } catch (e: Exception) {
            println("Caught $e")
        }
    }

    val scope2 = CoroutineScope(Job() + exceptionHandler)

    // No Crash too
    scope2.launch {
        try {
            supervisorScope {
                launch {
                    println("CEH: ${coroutineContext[CoroutineExceptionHandler]}")
                    throw RuntimeException()
                }
            }
        } catch (e: Exception) {
            println("Caught $e")
        }
    }

    Thread.sleep(100)
}