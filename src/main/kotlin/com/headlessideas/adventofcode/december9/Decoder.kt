package com.headlessideas.adventofcode.december9

import com.headlessideas.adventofcode.utils.readFile
import java.nio.CharBuffer

fun calculateGroupValue(stream: CharBuffer) {
    var currentLevel = 0
    var value = 0
    var garbage = 0
    var state = State.GROUP


    while (stream.hasRemaining()) {

        val current = stream.get()

        if (state == State.GARBAGE) {
            when (current) {
                '!' -> stream.get()
                '>' -> state = State.GROUP
                else -> if (state == State.GARBAGE) garbage++
            }
        } else {
            when (current) {
                '{' -> currentLevel++
                '!' -> stream.get()
                '<' -> state = State.GARBAGE
                '}' -> {
                    value += currentLevel
                    currentLevel--
                }
            }
        }
    }

    println("Part 1: $value")
    println("Part 2: $garbage")

}

fun main(args: Array<String>) {
    val stream = CharBuffer.wrap(readFile("9.dec.txt")[0])
    calculateGroupValue(stream)
}

enum class State {
    GROUP, GARBAGE
}