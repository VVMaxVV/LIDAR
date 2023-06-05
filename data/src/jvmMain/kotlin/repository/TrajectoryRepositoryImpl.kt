package repository

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import model.Point
import util.LimitedSizeList

internal class TrajectoryRepositoryImpl : TrajectoryRepository {
    private val trajectoryState = mutableStateOf<List<Point>>(emptyList())
    private val trajectoryList: LimitedSizeList<Point> = LimitedSizeList(100)
    override fun addPoint(point: Point) {
        trajectoryList.add(point)
        trajectoryState.value = trajectoryList.getAll()
    }

    override fun getTrajectory(): State<List<Point>> = trajectoryState
}
