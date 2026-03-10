package com.pdminh.playground.flow.terminal_operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.runBlocking

fun main() {

    val flow = flow {
        delay(100)

        println("Emitting first value")
        emit(1)

        delay(100)

        println("Emitting second value")
        emit(2)

        delay(100)

        println("Emitting third value")
        emit(2)
    }

    runBlocking {
        val item = flow.toSet()
        println("Received toSet() $item")
    }

    runBlocking {
        val item = flow.toList()
        println("Received toList() $item")
    }
}