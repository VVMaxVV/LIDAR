package repository

import factory.PathFactory
import kotlin.math.roundToInt
import model.GridSpace
import model.Point

private const val ARRAY_SIZE = 100

internal class PathRepositoryImpl(
    private val pathFactory: PathFactory
) : PathRepository {
    private val patencyArray = Array(ARRAY_SIZE) { BooleanArray(ARRAY_SIZE) { true } }

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
}
