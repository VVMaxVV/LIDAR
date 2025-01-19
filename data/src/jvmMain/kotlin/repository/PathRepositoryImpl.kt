package repository

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import factory.PathFactory
import kotlin.math.roundToInt
import model.GridSpace
import model.Point

private const val ARRAY_SIZE = 200

internal class PathRepositoryImpl(
    private val pathFactory: PathFactory
) : PathRepository, GoalPointRepository {
    private var patencyArray = Array(ARRAY_SIZE) { BooleanArray(ARRAY_SIZE) { true } }

    private val goalPoint = mutableStateOf<Point?>(null)

    override suspend fun getPath(start: Point, goal: Point) = pathFactory.get(
        start = Point(start.x.toDouble() + ARRAY_SIZE / 2 - 1, start.y.toDouble() + ARRAY_SIZE / 2 - 1),
        goal = Point(goal.x.toDouble() + ARRAY_SIZE / 2 - 1, goal.y.toDouble() + ARRAY_SIZE / 2 - 1),
        space = GridSpace(patencyArray)
    ).map {
        Point(it.x.toDouble() - ARRAY_SIZE / 2 + 1, it.y.toDouble() - ARRAY_SIZE / 2 + 1)
    }

    override suspend fun addObstacle(list: List<Point>) {
        val tempList = list.toList()
        tempList.forEach {
            patencyArray[it.x.toFloat().roundToInt() + (ARRAY_SIZE / 2 - 1)][it.y.toFloat().roundToInt() + (ARRAY_SIZE / 2 - 1)] = false
        }
    }

    override suspend fun clearMemorySpace() {
        patencyArray = Array(ARRAY_SIZE) { BooleanArray(ARRAY_SIZE) { true } }
    }

    override fun setGoalPoint(goal: Point) {
        goalPoint.value = goal
    }

    override fun getGoalPoint(): State<Point?> = goalPoint
}
