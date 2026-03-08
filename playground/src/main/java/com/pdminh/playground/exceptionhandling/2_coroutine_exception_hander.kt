package com.pdminh.playground.exceptionhandling

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun main() {

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Caught $throwable in CoroutineExceptionHandler")
    }

    val scope = CoroutineScope(Job())
    scope.launch(exceptionHandler) {
        throw RuntimeException()
    }

    // OR
//    val scope = CoroutineScope(Job() + exceptionHandler)
//    scope.launch() {
//        throw RuntimeException()
//    }

    // CRASH
    scope.launch {
        launch(exceptionHandler) {
            throw RuntimeException()
        }
    }

    Thread.sleep(100)
}