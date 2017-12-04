package com.headlessideas.adventofcode.december3

import kotlin.math.pow
import kotlin.math.sqrt

fun getDistance(start: Int): Int {

    println(start - sqrt(start.toFloat()).toInt().toFloat().pow(2))

    val ceilings = generateSequence(1) { ((it + 1) / 2 + 1) * 2 - 1 }

    val layer = ceilings.takeWhile { it.toDouble().pow(2) < start }.last() + 2
    println(layer)
    return (layer + 1) / 2
}

fun main(args: Array<String>) {
    val input = 289326
    println(getDistance(input))
}