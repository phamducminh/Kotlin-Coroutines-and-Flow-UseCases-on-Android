package com.pdminh.playground.flow.intermediate_operators

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.withIndex

suspend fun main() {

    flowOf(1, 2, 3, 4, 5)
        .withIndex()
        .collect { collectedValue ->
            println(collectedValue)
        }
}