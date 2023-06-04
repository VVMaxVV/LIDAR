package repository

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import factory.PathFactory
import model.GridSpace
import model.Point
import kotlin.math.roundToInt

private const val ARRAY_SIZE = 100

internal class PathRepositoryImpl(
    private val pathFactory: PathFactory
) : PathRepository, GoalPointRepository {
    private val patencyArray = Array(ARRAY_SIZE) { BooleanArray(ARRAY_SIZE) { true } }

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
            patencyArray[it.x.toFloat().roundToInt() + 49][it.y.toFloat().roundToInt() + 49] = false
        }
    }

    override fun setGoalPoint(goal: Point) {
        goalPoint.value = goal
    }

    override fun getGoalPoint(): State<Point?> = goalPoint
}
