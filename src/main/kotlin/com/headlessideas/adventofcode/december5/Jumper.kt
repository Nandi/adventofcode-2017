package com.headlessideas.adventofcode.december5

import com.headlessideas.adventofcode.utils.readFile

fun readInstructions(instructions: MutableList<Int>, f: (Int) -> Int): Int {
    var pos = 0
    var jumps = 0

    while (pos >= 0 && pos < instructions.size) {
        val tmp = instructions[pos]

        instructions[pos] = f(tmp)

        pos += tmp
        jumps++
    }

    return jumps
}

fun main(args: Array<String>) {
    val i = readFile("5.dec.txt").map { it.toInt() }
    println(readInstructions(i.toMutableList()) {
        it + 1
    })
    println(readInstructions(i.toMutableList()) {
        when (it) {
            3 -> it-1
            else -> it+1
        }
    })
}