package com.pdminh.playground.flow.exceptionhandling

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

suspend fun main(): Unit = coroutineScope {

    flow {
//        try {
            emit(1)
//        } catch (e: Exception) {
//            println("Catch exception in flow builder.")
//        }
    }.catch {
        println("Handled exception in catch operator")
    }.collect { emittedValue ->
        throw Exception("Exception in collect{}")
    }
}