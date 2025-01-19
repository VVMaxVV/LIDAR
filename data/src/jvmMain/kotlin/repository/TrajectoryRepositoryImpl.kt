package repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import model.Point
import util.LimitedSizeList

internal class TrajectoryRepositoryImpl : TrajectoryRepository {
    private val trajectoryState = MutableStateFlow<LimitedSizeList<Point>>(LimitedSizeList(255))

    override fun addPoint(point: Point) {
        trajectoryState.value = trajectoryState.value.also {
            it.add(point)
        }
    }

    override fun getTrajectory(): StateFlow<List<Point>> = trajectoryState

    override fun clearTrajectory() {
        trajectoryState.value.clear()
    }
}
