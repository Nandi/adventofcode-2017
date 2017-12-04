package com.headlessideas.adventofcode.december1

import com.headlessideas.adventofcode.utils.readFile

fun solve1(capcha: String): Int {
    val numbers = capcha.toCharArray().map { it.toString().toInt() }
    var sum = 0

    numbers.forEachIndexed { i, v ->
        val next = (i + 1) % numbers.size
        if (v == numbers[next]) {
            sum += v
        }
    }

    return sum
}

fun solve2(capcha: String): Int {
    val numbers = capcha.split("").drop(1).dropLast(1).map { it.toInt() }
    var sum = 0

    numbers.forEachIndexed { i, v ->
        val next = (i + (numbers.size / 2)) % numbers.size
        if (v == numbers[next]) {
            sum += v
        }
    }

    return sum
}

fun main(args: Array<String>) {
    println(solve1(readFile("1.dec.txt")[0]))
    println(solve2(readFile("1.dec.txt")[0]))
}