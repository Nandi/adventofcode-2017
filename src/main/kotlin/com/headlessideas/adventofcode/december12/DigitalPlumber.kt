package com.headlessideas.adventofcode.december12

import com.headlessideas.adventofcode.utils.readFile

fun mapGroup(start: Int, programs: Map<Int, List<Int>>): Int {

    val group = connections(start, programs)

    return group.size
}

fun countGroups(programs: Map<Int, List<Int>>): Int {
    val groups = mutableListOf<List<Int>>()

    val remaining = programs.toMutableMap()

    while (remaining.isNotEmpty()) {
        val group = connections(remaining.keys.first(), programs)
        group.forEach { remaining.remove(it) }
        groups.add(group)
    }

    return groups.size
}

fun connections(id: Int, programs: Map<Int, List<Int>>, current: MutableList<Int> = mutableListOf()): List<Int> {
    val pipes = programs[id]!!.filter { !current.contains(it) }
    if (pipes.isEmpty()) {
        return listOf(id)
    }

    current += pipes

    for (pipe in pipes) {
        connections(pipe, programs, current)
    }

    return current
}

fun main(args: Array<String>) {
    val programs = readFile("12.dec.txt").map {
        val parts = it.split(" <-> ")
        val id = parts[0].toInt()
        id to parts[1].split(", ").map { it.toInt() }.filter { it != id }
    }.toMap()

    println(mapGroup(0, programs))
    println(countGroups(programs))
}