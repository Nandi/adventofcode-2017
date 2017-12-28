package com.headlessideas.adventofcode.december14

import com.headlessideas.adventofcode.december10.KnotHash

fun defrag(input: String) {
    val disk = mutableListOf<MutableList<Int>>()
    (0..127)
            .map { KnotHash("$input-$it").hash() }
            .forEach { disk += mapHashToDisk(it) }

    val count = disk.flatMap { it }.count { it == -1 }

    println("Part 1: $count")
    var group = 1

    for (i in 0 until (128 * 128)) {
        val x = i % 128
        val y = i / 128
        if (disk[y][x] == -1) {
            disk.markGroup(x, y, group)
            group++
        }
    }

    println("Part 2: ${group - 1}")
}

private fun List<MutableList<Int>>.markGroup(x: Int, y: Int, group: Int) {
    get(y)[x] = group
    if (x > 0 && get(y)[x - 1] == -1) {
        markGroup(x - 1, y, group)
    }
    if (y < lastIndex && get(y + 1)[x] == -1) {
        markGroup(x, y + 1, group)
    }
    if (x < lastIndex && get(y)[x + 1] == -1) {
        markGroup(x + 1, y, group)
    }
    if (y > 0 && get(y - 1)[x] == -1) {
        get(y - 1)[x] = group
        markGroup(x, y - 1, group)
    }
}

fun mapHashToDisk(hash: String): MutableList<Int> {
    return hash.toBinaryString().toCharArray().map {
        when (it) {
            '0' -> 0
            else -> -1
        }
    }.toMutableList()
}

fun String.toBinaryString(): String {
    val sb = StringBuilder()
    toCharArray()
            .map { Integer.parseUnsignedInt(it.toString(), 16) }
            .forEach { sb.append(Integer.toBinaryString(it).padStart(4, '0')) }


    return sb.toString()
}

fun main(args: Array<String>) {
    defrag("hwlqcszp")
}