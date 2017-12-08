package com.headlessideas.adventofcode.december6

import com.headlessideas.adventofcode.utils.readFile

fun memRemap(profile: MutableList<Int>) {

    val snapshots = mutableListOf<List<Int>>()

    do {
        snapshots.add(profile.map { it })

        var index = profile.indexOf(profile.max())
        var max = profile[index]
        profile[index] = 0

        while (max > 0) {
            index = (index + 1) % profile.size

            profile[index]++

            max--
        }

    } while (!snapshots.contains(profile))

    println("Part 1 : ${snapshots.size}")
    println("Part 2 : ${snapshots.size - snapshots.indexOf(profile)}")
}

fun main(args: Array<String>) {
    val input = readFile("6.dec.txt")[0].split("\t").map { it.toInt() }.toMutableList()
    memRemap(input)
}