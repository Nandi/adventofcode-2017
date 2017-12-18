package com.headlessideas.adventofcode.december15

fun judge(genA: Generator, genB: Generator, iterations: Int) {
    var count = 0
    (1..iterations).forEach {
        val a = genA.generate()
        val b = genB.generate()
        if (a == b) count++

    }

    println("$count")
}

fun main(args: Array<String>) {
    judge(Generator(722, 16807), Generator(354, 48271), 40_000_000)
    judge(Generator(722, 16807, 4), Generator(354, 48271, 8), 5_000_000)
}



class Generator(start: Int, private val factor: Int, private val multiplier: Int = 1) {
    private val divider = 2147483647
    private var prev = start.toLong()

    fun generate(): Short {
        do {
            prev = ((prev * factor) % divider)
        } while (prev.toInt() % multiplier != 0)

        return prev.toShort()
    }
}