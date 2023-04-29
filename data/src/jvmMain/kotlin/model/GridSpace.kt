package model

internal class GridSpace(private val grid: Array<BooleanArray>) {
    fun getNeighbors(point: Point): List<Point> {
        val neighbors = mutableListOf<Point>()
        val x = point.x.toInt()
        val y = point.y.toInt()

        // Add top neighbor
        if (y > 0 && grid[x][y - 1]) {
            neighbors.add(Point(x.toDouble(), (y - 1).toDouble()))
        }

        // Add bottom neighbor
        if (y < grid[x].size - 1 && grid[x][y + 1]) {
            neighbors.add(Point(x.toDouble(), (y + 1).toDouble()))
        }

        // Add left neighbor
        if (x > 0 && grid[x - 1][y]) {
            neighbors.add(Point((x - 1).toDouble(), y.toDouble()))
        }

        // Add right neighbor
        if (x < grid.size - 1 && grid[x + 1][y]) {
            neighbors.add(Point((x + 1).toDouble(), y.toDouble()))
        }

        return neighbors
    }
}
