package repository

import androidx.compose.ui.geometry.Offset
import factory.RaysFactory
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
import util.getPointIntersectionOfLines

internal class LidarDataRepositoryImpl(
    private val obstacleRepositoryImpl: ObstaclesRepository,
    private val raysFactory: RaysFactory,
    private val pointMapper: PointMapper,
    private val offsetMapper: OffsetMapper
) : LidarDataRepository {
    private var _rayTracingConfiguration: RayTracingConfiguration? = null
    private var _position: Position? = null

    private var _listPointInterception = mutableListOf<Point>()

    override fun getDistanceToObstaclesCollision(): List<DistanceToCollision> {
        val distanceToIntersection = mutableListOf<DistanceToCollision>()
        _rayTracingConfiguration?.let { configuration ->
            _position?.let { position ->
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
                    raysFactory.get(configuration, position).forEach { ray ->
                        getDistanceToAllIntersectionForRay(ray, obstacleList).also { allIntersectionDistanceForRay ->
                            getNearestInterceptionPoint(allIntersectionDistanceForRay)?.also {
                                distanceToIntersection.add(toNearestObstacle(position.currentCoordinates.getDistance(it)))
                            }
                        }
                    }
                }
            }
        }
        return distanceToIntersection
    }

    override fun setupConfiguration(configuration: RayTracingConfiguration) {
        _rayTracingConfiguration = configuration
    }

    override fun setCurrentPosition(currentPosition: Position) {
        _position = currentPosition
    }

    override fun getPointsInterception() = _listPointInterception

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
        _position?.let { position ->
            if (allPointsIntersection.filterNotNull().isNotEmpty()) {
                _listPointInterception.clear()
                return allPointsIntersection.filterNotNull().minBy { position.currentCoordinates.getDistance(it) }
                    .also { _listPointInterception.add(it) }
            }
        }
        return null
    }

    private fun toNearestObstacle(
        length: Number
    ): DistanceToCollision {
        _rayTracingConfiguration?.let {
            if (it.maxLength > length) return DistanceToCollision.WithinMeasurement(length)
        }
        return DistanceToCollision.OutOfBound
    }
}
