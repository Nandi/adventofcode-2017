package com.headlessideas.adventofcode.december13

import com.headlessideas.adventofcode.utils.readFile

fun crossFirewall(firewalls: Array<Firewall?>, delay: Int = 0): Pair<Int, Boolean> {
    var pos = -1 - delay
    var severity = 0
    var detected = false

    while (pos < firewalls.lastIndex) {
        pos++
        if (pos >= 0) {
            firewalls[pos]?.let {
                if (it.detected()) {
                    severity += it.severity()
                    detected = true
                }
            }
        }
        firewalls.forEach { it?.move() }
    }

    return severity to detected
}

fun main(args: Array<String>) {
    val map = readFile("13.dec.txt").map {
        val parts = it.split(": ")
        parts[0].toInt() to Firewall(parts[0].toInt(), parts[1].toInt())
    }.toMap()

    val firewalls = Array(map.keys.last() + 1) {
        map[it]
    }

    println(crossFirewall(firewalls.copyOf()).first)

    var delay = 0
    while (true) {
        firewalls.forEach { it?.reset() }
        if (!firewalls.any { it?.detected(delay) == true }) break
        delay++
    }

    println(delay)
}

class Firewall(private val id: Int, private val depth: Int) {

    var direction: Direction = Direction.DOWN
    var pos: Int = TOP

    fun move() {
        pos += when (direction) {
            Direction.UP -> -1
            Direction.DOWN -> 1
        }

        if (pos == depth) {
            direction = Direction.UP
        } else if (pos == TOP) {
            direction = Direction.DOWN
        }
    }

    fun reset() {
        pos = TOP
        direction = Direction.DOWN
    }

    fun detected() = pos == 1

    fun detected(time: Int) = (time + id) % ((depth - 1) * 2) == 0

    fun severity() = id * depth

    companion object {
        private val TOP = 1
    }
}

enum class Direction {
    UP, DOWN
}