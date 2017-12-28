package com.headlessideas.adventofcode.december16

import com.headlessideas.adventofcode.utils.readFile

fun dance(moves: List<Move>) {
    var programs = "abcdefghijklmnop".toCharArray().toMutableList()
    val seen = mutableListOf<String>()
    val repetitions = 1_000_000_000

    for (i in 0..repetitions) {
        if (seen.contains(programs.joinToString(""))) {
            println("Part 2: ${seen[repetitions % i]}")
            break
        }

        seen.add(programs.joinToString(""))

        for (move in moves) {
            when (move) {
                is Spin -> programs = move.spin(programs)
                is Exchange -> move.exchange(programs)
                is Partner -> move.partner(programs)
            }
        }
    }

    println("Part 1: ${seen[1]}")

}

fun main(args: Array<String>) {
    val moves = readFile("16.dec.txt").flatMap { it.split(",") }.map {
        when (it.first()) {
            's' -> Spin(it)
            'x' -> Exchange(it)
            else -> Partner(it)
        }
    }

    dance(moves)
}

sealed class Move

class Spin(input: String) : Move() {
    private val count: Int = input.substring(1).toInt()

    fun spin(programs: MutableList<Char>): MutableList<Char> {
        return (programs.takeLast(count) + programs.dropLast(count)).toMutableList()
    }
}

class Exchange(input: String) : Move() {
    private val a: Int
    private val b: Int

    init {
        val parts = input.substring(1).split("/")
        a = parts[0].toInt()
        b = parts[1].toInt()
    }

    fun exchange(programs: MutableList<Char>) {
        val tmp = programs[a]
        programs[a] = programs[b]
        programs[b] = tmp
    }
}

class Partner(input: String) : Move() {
    private val a: Char
    private val b: Char

    init {
        val parts = input.substring(1).split("/")
        a = parts[0].first()
        b = parts[1].first()
    }

    fun partner(programs: MutableList<Char>) {

        val indexA = programs.indexOf(a)
        val indexB = programs.indexOf(b)

        val tmp = programs[indexA]
        programs[indexA] = programs[indexB]
        programs[indexB] = tmp
    }
}