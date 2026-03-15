package com.pdminh.playground.flow.hot_and_cold_flows

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

fun coldFlow() = flow {
    println("Emitting 1")
    emit(1)

    delay(1_000)
    println("Emitting 2")
    emit(2)

    delay(1_000)
    println("Emitting 3")
    emit(3)
}

suspend fun main(): Unit = coroutineScope {

    launch {
        coldFlow()
            .onCompletion {
                println("Flow of Collector 1 completed")
            }
            .collect {
                println("Collector 1 collects: $it")
            }
    }

    launch {
        coldFlow()
            .onCompletion {
                println("Flow of Collector 2 completed")
            }
            .collect {
                println("Collector 2 collects: $it")
            }
    }
}