package repository

import androidx.compose.runtime.State
import kotlinx.coroutines.flow.StateFlow
import model.Point

interface TrajectoryRepository {
    fun addPoint(point: Point)
    fun getTrajectory(): StateFlow<List<Point>>
    fun clearTrajectory()
}
