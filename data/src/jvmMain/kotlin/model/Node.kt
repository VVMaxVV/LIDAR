package model

internal class Node(val point: Point, var parent: Node?) : Comparable<Node> {
    var g: Double = 0.0
    var h: Double = 0.0
    var f: Double = 0.0

    override fun compareTo(other: Node): Int {
        return when {
            f < other.f -> -1
            f > other.f -> 1
            else -> 0
        }
    }
}
