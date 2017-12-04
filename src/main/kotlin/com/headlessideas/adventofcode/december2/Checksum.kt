package com.headlessideas.adventofcode.december2

import com.headlessideas.adventofcode.utils.readFile

fun checkSum(rows: List<String>, f: (List<Int>) -> Int): Int {
    var sum = 0
    rows.map { row -> row.split("\t").map { it.toInt() } }
            .forEach { sum += f(it) }

    return sum
}

fun main(args: Array<String>) {
    val rows = readFile("2.dec.txt")
    println(checkSum(rows) {
        val s = it.sorted()
        s.last() - s.first()
    })

    println(checkSum(rows) {

        it.forEach { n1 ->
            it.filter { n1 != it && n1 % it == 0 }
                    .forEach { return@checkSum n1 / it }
        }
        0
    })
}