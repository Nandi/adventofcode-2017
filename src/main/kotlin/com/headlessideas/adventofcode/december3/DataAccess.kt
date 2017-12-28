package com.headlessideas.adventofcode.december3

import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.sqrt

fun getDistance(input: Int): Int {

    val circle = ceil(sqrt(input.toFloat()) / 2)
    val circleZero = (circle * 2 - 1).pow(2)
    val centers = (1..7 step 2).map { circleZero + it * circle }
    val distance = circle + centers.map { abs(input - it) }.min()!!
    return distance.toInt()
}

fun main(args: Array<String>) {
    val input = 289326
    println(getDistance(input))
    //No part 2 for me
}