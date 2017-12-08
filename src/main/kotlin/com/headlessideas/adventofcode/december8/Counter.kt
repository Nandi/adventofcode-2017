package com.headlessideas.adventofcode.december8

import com.headlessideas.adventofcode.utils.readFile
import kotlin.math.max

fun registerCounter(instructions: List<Instruction>) {
    val register = mutableMapOf<String, Int>()
    var maxValue = 0
    instructions.forEach {
        it.run(register)
        maxValue = checkMax(register, maxValue)
    }

    println(register.maxBy { it.value }?.value ?: "Register empty")
    println(maxValue)
}

fun checkMax(register: MutableMap<String, Int>, maxValue: Int): Int {
    return max(register.maxBy { it.value }?.value ?: 0, maxValue)
}

fun main(args: Array<String>) {
    val r = readFile("8.dec.txt").map {
        val parts = it.split(" ")
        Instruction(parts[0],
                type(parts[1]),
                parts[2].toInt(),
                Logic.create(parts[4], parts[5], parts[6]))
    }
    registerCounter(r)
}

class Instruction(private val register: String, private val type: Type, private val amount: Int, private val logic: Logic) {
    fun run(registers: MutableMap<String, Int>) {
        var current = registers.getOrDefault(register, 0)

        if (logic.test(registers)) {
            current += when (type) {
                Type.INC -> amount
                Type.DEC -> -amount
            }
        }

        registers[register] = current
    }
}

class Logic(private val left: String, private val condition: Condition, private val right: Int) {
    fun test(registers: Map<String, Int>): Boolean {
        val amount = registers[left] ?: 0

        return when (condition) {
            Condition.BANG_EQUAL -> amount != right
            Condition.EQUAL_EQUAL -> amount == right
            Condition.GREATER -> amount > right
            Condition.GREATER_EQUAL -> amount >= right
            Condition.LESS -> amount < right
            Condition.LESS_EQUAL -> amount <= right
        }
    }

    companion object {
        fun create(left: String, condition: String, right: String): Logic {
            val c = when (condition) {
                "!=" -> Condition.BANG_EQUAL
                "==" -> Condition.EQUAL_EQUAL
                ">" -> Condition.GREATER
                ">=" -> Condition.GREATER_EQUAL
                "<" -> Condition.LESS
                "<=" -> Condition.LESS_EQUAL
                else -> Condition.EQUAL_EQUAL
            }

            return Logic(left, c, right.toInt())
        }
    }
}

enum class Condition {
    BANG_EQUAL, EQUAL_EQUAL, GREATER, GREATER_EQUAL, LESS, LESS_EQUAL
}

enum class Type {
    INC, DEC
}

fun type(t: String): Type {
    return when (t) {
        "inc" -> Type.INC
        "dec" -> Type.DEC
        else -> Type.INC
    }
}