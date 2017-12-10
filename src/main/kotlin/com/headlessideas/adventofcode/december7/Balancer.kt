package com.headlessideas.adventofcode.december7

import com.headlessideas.adventofcode.utils.readFile

fun findBottom(programs: List<Program>): Program {
    val children = programs.flatMap { it.children }
    return programs.filter { it.children.isNotEmpty() && !children.contains(it) }[0]
}

fun findUnbalance(bottom: Program): Int {

    val unbalanced = bottom.children.firstOrNull { !it.isBalanced() }

    if (unbalanced == null) {
        val weightMap = bottom.children.groupBy { it.programWeight() }
        val unbalance = weightMap.filter { it.value.size == 1 }.toList().first().first
        val balance = weightMap.filter { it.value.size != 1 }.toList().first().first

        return weightMap[unbalance]!![0].balancedWeight(balance - unbalance)
    }

    return findUnbalance(unbalanced)
}

fun main(args: Array<String>) {
    val input = readFile("7.dec.txt").map {
        val parts = it.split(" -> ")
        parts[0].split(" ")[0] to it
    }.toMap()

    val programs = mutableListOf<Program>()

    for (p in input) {
        if (programs.all { it.name != p.key }) {
            programs.add(Program.create(p.value, input))
        }

    }
    val bottom = findBottom(programs)
    println("Part 1: ${bottom.name}")
    println("Part 2: ${findUnbalance(bottom)}")

}

data class Program(val name: String, private val weight: Int, val children: List<Program>) {

    fun programWeight(): Int {
        return children.map { it.programWeight() }.sum() + weight
    }

    fun isBalanced() = children.map { it.programWeight() }.distinct().size == 1

    fun balancedWeight(diff: Int) = weight + diff

    companion object {
        fun create(input: String, programStrings: Map<String, String>): Program {
            val family = input.split(" -> ")

            val parent = family[0].split(" ")
            val weight = parent[1].drop(1).dropLast(1)

            val children = if (family.size == 2) {
                family[1].split(", ").map { Program.create(programStrings[it]!!, programStrings) }
            } else {
                listOf()
            }

            return Program(parent[0], weight.toInt(), children)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Program) return false
        if (other.name == name) return true
        return false
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}