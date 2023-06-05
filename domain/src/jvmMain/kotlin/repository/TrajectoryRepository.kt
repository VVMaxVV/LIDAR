package repository

import androidx.compose.runtime.State
import model.Point

interface TrajectoryRepository {
    fun addPoint(point: Point)
    fun getTrajectory(): State<List<Point>>
    fun clearTrajectory()
}
