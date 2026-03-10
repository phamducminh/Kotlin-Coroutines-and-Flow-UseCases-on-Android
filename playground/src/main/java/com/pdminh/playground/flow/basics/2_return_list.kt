package com.pdminh.playground.flow.basics

import com.pdminh.playground.utils.printWithTimePassed
import java.math.BigInteger

fun main() {
    val startTime = System.currentTimeMillis()
    calculateFactorialOf(5).forEach {
        printWithTimePassed(message = it, startTime = startTime)
    }
}

// factorial of n (n!) = 1 * 2 * 3 * 4 * ... * n
private fun calculateFactorialOf(number: Int): List<BigInteger> = buildList {
    var factorial = BigInteger.ONE
    for (i in 1..number) {
        Thread.sleep(10)
        factorial = factorial.multiply(BigInteger.valueOf(i.toLong()))
        add(factorial)
    }
}