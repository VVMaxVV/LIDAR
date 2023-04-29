package factory

import model.GridSpace
import model.Node
import model.Point
import util.minus
import java.util.PriorityQueue
import kotlin.math.sqrt

internal class PathFactory {
    fun get(start: Point, goal: Point, space: GridSpace): List<Point> {
        val openList = PriorityQueue<Node>()
        val closedList = hashSetOf<Node>()
        val startNode = Node(Point(start.x.toDouble(), start.y.toDouble()), null)
        startNode.g = 0.0
        startNode.h = heuristic(Point(start.x.toDouble(), start.y.toDouble()), goal)
        startNode.f = startNode.g + startNode.h
        openList.add(startNode)
        while (openList.isNotEmpty()) {
            val current = openList.poll()
            if (current.point == goal) {
                return getPath(current)
            }
            closedList.add(current)
            for (neighbor in space.getNeighbors(current.point)) {
                val tentativeGScore = current.g + distance(current.point, neighbor)
                val neighborNode = Node(neighbor, current)
                neighborNode.g = tentativeGScore
                neighborNode.h = heuristic(neighbor, goal)
                neighborNode.f = neighborNode.g + neighborNode.h
                if (closedList.any { it.point == neighborNode.point } && tentativeGScore >= neighborNode.g) {
                    continue
                }
                if (openList.any { it.point == neighborNode.point } && tentativeGScore >= neighborNode.g) {
                    continue
                }
                openList.add(neighborNode)
            }
        }
        return emptyList()
    }

    private fun getPath(node: Node): List<Point> {
        val path = mutableListOf<Point>()
        var current = node
        while (current.parent != null) {
            path.add(current.point)
            current = current.parent!!
        }
        path.add(current.point)
        return path.reversed()
    }

    private fun distance(point1: Point, point2: Point): Double {
        val dx = (point1.x - point2.x).toDouble()
        val dy = (point1.y - point2.y).toDouble()
        return sqrt(dx * dx + dy * dy)
    }

    private fun heuristic(point: Point, goal: Point): Double {
        return distance(point, goal)
    }
}
