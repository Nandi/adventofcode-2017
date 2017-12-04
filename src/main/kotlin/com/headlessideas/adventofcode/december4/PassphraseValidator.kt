package com.headlessideas.adventofcode.december4

import com.headlessideas.adventofcode.utils.readFile

fun validate(passphrases: List<List<String>>) {
    println(passphrases.filter { it.isDistinct() }.size)
    println(passphrases.filter { it.noAnagrams() }.size)
}

fun List<String>.isDistinct(): Boolean = distinct().size == size

fun List<String>.noAnagrams(): Boolean = distinctBy { it.toCharArray().sorted() }.size == size

fun main(args: Array<String>) {
    val lines = readFile("4.dec.txt")
    validate(lines.map { it.split(" ") })
}