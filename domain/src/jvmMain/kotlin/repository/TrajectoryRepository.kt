package repository

import kotlinx.coroutines.flow.StateFlow
import model.Point

interface TrajectoryRepository {
    suspend fun addPoint(point: Point)
    fun getTrajectory(): StateFlow<List<Point>>
    suspend fun clearTrajectory()
}
