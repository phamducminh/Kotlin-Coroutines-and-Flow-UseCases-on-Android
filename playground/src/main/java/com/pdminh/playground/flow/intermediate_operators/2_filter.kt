package com.pdminh.playground.flow.intermediate_operators

import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapNotNull

suspend fun main() {

    flowOf(1, 2, 3, 4, 5)
        .filter { it > 3 }
        .filterNot { it > 3 }
        .filterNotNull()
        .filterIsInstance<Int>()
        .collect { collectedValue ->
            println(collectedValue)
        }
}