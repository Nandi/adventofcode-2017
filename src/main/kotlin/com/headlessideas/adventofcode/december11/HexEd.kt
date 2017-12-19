package com.headlessideas.adventofcode.december11

import com.headlessideas.adventofcode.utils.readFile
import kotlin.math.max

fun traverseHexGrid(path: List<String>): Pair<Int, Int> {
    var x = 0
    var y = 0
    var furthest = 0

    for (step in path) {
        when (step) {
            "n" -> y++
            "s" -> y--
            "nw" -> {
                x--
                y++
            }
            "ne" -> x++
            "sw" -> x--
            "se" -> {
                x++
                y--
            }
        }

        furthest = max(furthest, hexDistance(x, y))
    }

    println("Part 2: $furthest")

    return x to y
}

fun hexDistance(x: Int, y: Int): Int {
    val z = -x - y
    return max(max(x, y), z)
}

fun main(args: Array<String>) {
    val path = readFile("11.dec.txt").joinToString("").split(",")

    val (x, y) = traverseHexGrid(path)
    println("Part 1: ${hexDistance(x, y)}")
}