package com.lukaslechner.coroutineusecasesonandroid.playground.structuredconcurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    val scopeJob = Job()
    val scope = CoroutineScope(Dispatchers.Default + scopeJob)

//    var childCoroutineJob: Job? = null
    val passedJob = Job()
    val coroutineJob = scope.launch(passedJob) {
//        childCoroutineJob = launch {
//            println("Starting child coroutine")
//            delay(1000)
//        }
        println("Starting coroutine")
        delay(1000)
    }

    Thread.sleep(1000)

    println("passedJob and coroutineJob are referenced to the same job: ${passedJob === coroutineJob}") // false
//    println("Is childCoroutineJob a child of coroutineJob? => ${coroutineJob.children.contains(childCoroutineJob)}") // true
    println("Is coroutineJob a child of scopeJob? => ${scopeJob.children.contains(coroutineJob)}") // true
}