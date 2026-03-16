package com.pdminh.playground.flow.concurrency

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest

suspend fun main() = coroutineScope {

    // mapLatest is useful in situation where we have a slow operation
    // in its block, and we again don't care about outdated emissions.
    // So with mapLatest, every time there's a new emission in the upstream,
    // its block is canceled and restarted with a new value.
    val flow = flow {
        repeat(5) {
            println("Emitter:   Start Cooking Pancake ${it + 1}")
            delay(100)
            println("Emitter:   Pancake ${it + 1} ready!")
            emit(it)
        }
    }.mapLatest {
        println("Add topping onto the pancake ${it + 1}")
        delay(200)
        it
    }

    flow.collect {
        println("Collector:   Start eating pancake ${it + 1}")
        delay(300)
        println("Collector:   Finished eating pancake ${it + 1}")
    }
}