package com.pdminh.playground.flow.concurrency

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow

suspend fun main() = coroutineScope {

    // With conflate, we have the same behavior as we had when we
    // use the buffer with a capacity of 1 and DROP_OLDEST as the
    // onBufferOverflow behavior.

    // The behavior of conflate is similar to a StateFlow. In both,
    // only the one most recent item is buffered, and when a new item
    // is emitted, the old item of the buffer is replaced by the new one.

    // So the conflate operator is useful in situation where only
    // the most recent item is relevant and intermediate values can
    // be skipped when a collector is too slow to process them.
    val flow = flow {
        repeat(5) {
            println("Emitter:   Start Cooking Pancake $it")
            delay(100)
            println("Emitter:   Pancake $it ready!")
            emit(it)
        }
    }.conflate()

    flow.collect {
        println("Collector:   Start eating pancake $it")
        delay(300)
        println("Collector:   Finished eating pancake $it")
    }
}