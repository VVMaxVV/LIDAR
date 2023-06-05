package util

import java.util.LinkedList

class LimitedSizeList<T>(private val maxSize: Int) : List<T> {
    private val list: LinkedList<T> = LinkedList()

    fun add(element: T) {
        list.addLast(element)
        if (list.size > maxSize) {
            list.removeFirst()
        }
    }

    fun clear() {
        list.clear()
    }

    override fun iterator(): Iterator<T> = list.iterator()

    override fun listIterator(): ListIterator<T> = list.listIterator()

    override fun listIterator(index: Int): ListIterator<T> = list.listIterator(index)

    override fun subList(fromIndex: Int, toIndex: Int): List<T> = list.subList(fromIndex, toIndex)

    override fun contains(element: T): Boolean = list.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = list.containsAll(elements)

    override val size: Int
        get() = list.size

    override fun get(index: Int): T {
        if (index !in 0 until size) {
            throw IndexOutOfBoundsException("Index: $index, Size: $size")
        }
        return list[index]
    }

    override fun indexOf(element: T): Int = list.indexOf(element)

    override fun isEmpty(): Boolean = list.isEmpty()

    override fun lastIndexOf(element: T): Int = list.lastIndexOf(element)
}
