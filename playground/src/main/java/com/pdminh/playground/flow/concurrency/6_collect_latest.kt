package com.pdminh.playground.flow.concurrency

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow

suspend fun main() = coroutineScope {

    val flow = flow {
        repeat(5) {
            val pancakeIndex = it + 1
            println("Emitter:   Start Cooking Pancake $pancakeIndex")
            delay(100)
            println("Emitter:   Pancake $pancakeIndex ready!")
            emit(it)
        }
    }

    // Every time the upstream emits a new item, the collectLatest block is
    // immediately canceled and restarted with this new item.
    // So collectLatest is useful when you have to slow collector, and you
    // only care about the most recent emission, and therefore you don't
    // want to process outdated emissions.
    flow.collectLatest {
        val pancakeIndex = it + 1
        println("Collector:   Start eating pancake $pancakeIndex")
        delay(300)
        println("Collector:   Finished eating pancake $pancakeIndex")
    }
}