package repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import model.Point
import util.BoundedFlow
import util.LimitedSizeList

internal class TrajectoryRepositoryImpl : TrajectoryRepository {
    private val trajectoryState = MutableStateFlow<LimitedSizeList<Point>>(LimitedSizeList(255))
    private val test = BoundedFlow<Point>(255)

    override suspend fun addPoint(point: Point) {
        test.add(point)
//        trajectoryState.value = trajectoryState.value.also {
//            it.add(point)
//        }
//        trajectoryState.value.add(point)
    }

    override fun getTrajectory(): StateFlow<List<Point>> = test.state

    override suspend fun clearTrajectory() {
//        trajectoryState.value.clear()'
        test.clear()
    }
}
