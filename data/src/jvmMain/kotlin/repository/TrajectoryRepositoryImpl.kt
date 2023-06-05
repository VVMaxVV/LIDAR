package repository

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import model.Point
import util.LimitedSizeList

internal class TrajectoryRepositoryImpl : TrajectoryRepository {
    private val trajectoryState = mutableStateOf<LimitedSizeList<Point>>(LimitedSizeList(255))

    override fun addPoint(point: Point) {
        trajectoryState.value = trajectoryState.value.also {
            it.add(point)
        }
    }

    override fun getTrajectory(): State<List<Point>> = trajectoryState

    override fun clearTrajectory() {
        trajectoryState.value.clear()
    }
}
