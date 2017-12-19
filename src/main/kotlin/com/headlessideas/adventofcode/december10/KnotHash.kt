package com.headlessideas.adventofcode.december10

fun main(args: Array<String>) {
    val input = "147,37,249,1,31,2,226,0,161,71,254,243,183,255,30,70"

    val knotHash = KnotHash(input)
    println(knotHash.hash())
}

class KnotHash(input: String) {
    private val seed = input.toCharArray().map { it.toInt() } + listOf(17, 31, 73, 47, 23)
    private val sparseHash = IntArray(256, { it })
    private var current = 0
    private var skip = 0
    private lateinit var hash: String

    fun hash(): String {
        for (i in 1..64) {
            calculateSparseHash()
        }

        hash = sparseHash.asSequence()
                .batch(16)
                .map { it.reduce { a, b -> a.xor(b) } }
                .map { it.toString(16).padStart(2, '0') }
                .joinToString(separator = "")

        return hash
    }

    private fun calculateSparseHash() {
        for (length in seed) {
            val subArray = sparseHash.subArray(current, length).reversedArray()
            sparseHash.setAll(current, subArray)
            current = (current + length + skip) % sparseHash.size
            skip++
        }
    }
}

fun IntArray.subArray(start: Int, length: Int): IntArray {
    val sub = (start..(start + length - 1)).map { get(it % size) }
    return sub.toIntArray()
}

fun IntArray.setAll(pos: Int, values: IntArray) {
    values.forEachIndexed { i, v -> set((pos + i) % size, v) }
}

//https://stackoverflow.com/questions/34498368/kotlin-convert-large-list-to-sublist-of-set-partition-size
fun <T> Sequence<T>.batch(n: Int): Sequence<List<T>> {
    return BatchingSequence(this, n)
}

private class BatchingSequence<T>(val source: Sequence<T>, val batchSize: Int) : Sequence<List<T>> {
    override fun iterator(): Iterator<List<T>> = object : AbstractIterator<List<T>>() {
        val iterate = if (batchSize > 0) source.iterator() else emptyList<T>().iterator()
        override fun computeNext() {
            if (iterate.hasNext()) setNext(iterate.asSequence().take(batchSize).toList())
            else done()
        }
    }
}