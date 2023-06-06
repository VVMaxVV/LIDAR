package repository

import model.Point

interface PathRepository {
    suspend fun getPath(start: Point, goal: Point): List<Point>
    suspend fun addObstacle(list: List<Point>)
    suspend fun clearMemorySpace()
}
