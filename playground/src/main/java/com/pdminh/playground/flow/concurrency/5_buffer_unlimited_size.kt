package com.pdminh.playground.flow.concurrency

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
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
    }.buffer(capacity = UNLIMITED)

    flow.collect {
        val pancakeIndex = it + 1
        println("Collector:   Start eating pancake $pancakeIndex")
        delay(300)
        println("Collector:   Finished eating pancake $pancakeIndex")
    }
}