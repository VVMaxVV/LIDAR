package repository

import kotlinx.coroutines.flow.StateFlow
import model.Point

interface PathRepository {
    suspend fun getPath(start: Point, goal: Point): List<Point>
    suspend fun addObstacle(list: List<Point>)
    suspend fun clearMemorySpace()
    fun getStoredPath(): StateFlow<List<Point>>
}
