package repository

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import factory.RaysFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mapper.OffsetMapper
import mapper.PointMapper
import model.AABBBox
import model.DistanceToCollision
import model.Line
import model.Point
import model.Position
import model.Ray
import model.RayTracingConfiguration
import util.compareTo
import util.div
import util.getPointIntersectionOfLines
import util.minus

internal class LidarDataRepositoryImpl(
    private val obstacleRepositoryImpl: ObstaclesRepository,
    private val raysFactory: RaysFactory,
    private val pointMapper: PointMapper,
    private val offsetMapper: OffsetMapper
) : LidarDataRepository, CurrentPositionRepository {
    private var rayTracingConfiguration: RayTracingConfiguration? = null
    private var position = mutableStateOf<Position?>(null)

    private val distanceToCollisionFlow = MutableStateFlow<List<DistanceToCollision>>(emptyList())
    private val rayListFlow = MutableStateFlow<List<Ray>>(emptyList())

    private var listPointInterception = mutableListOf<Point>()

    override fun getDistanceToObstaclesCollision(): StateFlow<List<DistanceToCollision>> = distanceToCollisionFlow

    override fun getRays(): Flow<List<Ray>> = rayListFlow

    override fun setupConfiguration(configuration: RayTracingConfiguration) {
        rayTracingConfiguration = configuration
    }

    override fun getPointsInterception() = listPointInterception

    override fun getCurrentPosition(): State<Position?> = position

    override suspend fun setCurrentPosition(position: Position) {
        this.position.value = position
        val distanceToIntersection = mutableListOf<DistanceToCollision>()
        rayTracingConfiguration?.let { configuration ->
            val aabbBox: AABBBox?
            raysFactory.get(
                RayTracingConfiguration(3, configuration.horizontalFov, configuration.maxLength),
                position
            ).also { rayList ->
                aabbBox =
                    pointMapper.getAABBBox(
                        mutableSetOf(offsetMapper.toPoint(rayList[0].start)).also { pointSet ->
                            rayList.forEach {
                                pointSet.add(offsetMapper.toPoint(it.end))
                            }
                        }.toList()
                    )
            }
            aabbBox?.let { box ->
                val obstacleList = obstacleRepositoryImpl.getLinesWithinPoints(box.firstPoint, box.secondPoint)
                listPointInterception.clear()
                raysFactory.get(
                    RayTracingConfiguration(
                        configuration.numbersOfRay - 1,
                        configuration.horizontalFov - configuration.horizontalFov / configuration.numbersOfRay,
                        configuration.maxLength
                    ),
                    position
                ).also { rayListFlow.value = it }.forEach { ray ->
                    getDistanceToAllIntersectionForRay(ray, obstacleList).also { allIntersectionDistanceForRay ->
                        getNearestInterceptionPoint(allIntersectionDistanceForRay)?.also { point ->
                            listPointInterception.add(point)
                            toNearestObstacle(position.currentCoordinates.getDistance(point)).also {
                                distanceToIntersection.add(it)
                            }
                        }
                    }
                }
            }
        }
        distanceToCollisionFlow.emit(distanceToIntersection)
    }

    private fun getDistanceToAllIntersectionForRay(ray: Ray, obstaclesList: List<Line>): List<Point?> {
        val allIntersectionDistanceForRay = mutableListOf<Point?>()
        obstaclesList.forEach { line ->
            getPointIntersectionOfLines(
                ray,
                Ray(
                    Offset(line.startPoint.x.toFloat(), line.startPoint.y.toFloat()),
                    Offset(
                        line.endPoint.x.toFloat(),
                        line.endPoint.y.toFloat()
                    )
                )
            ).also {
                allIntersectionDistanceForRay.add(it)
            }
        }
        return allIntersectionDistanceForRay
    }

    private fun getNearestInterceptionPoint(
        allPointsIntersection: List<Point?>
    ): Point? {
        position.value?.let { position ->
            if (allPointsIntersection.filterNotNull().isNotEmpty()) {
                return allPointsIntersection.filterNotNull().minBy { position.currentCoordinates.getDistance(it) }
            }
        }
        return null
    }

    private fun toNearestObstacle(
        length: Number
    ): DistanceToCollision {
        rayTracingConfiguration?.let {
            if (it.maxLength > length) return DistanceToCollision.WithinMeasurement(length)
        }
        return DistanceToCollision.OutOfBound
    }
}
