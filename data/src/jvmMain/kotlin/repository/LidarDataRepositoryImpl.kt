package repository

import androidx.compose.ui.geometry.Offset
import factory.RaysFactory
import model.DistanceToCollision
import model.Obstacle
import model.Position
import model.Ray
import model.RayTracingConfiguration
import util.compareTo
import util.getDistanceToIntersection

internal class LidarDataRepositoryImpl(
    private val obstacleRepositoryImpl: ObstaclesRepository,
    private val raysFactory: RaysFactory
) : LidarDataRepository {
    private var _rayTracingConfiguration: RayTracingConfiguration? = null
    private var _position: Position? = null

    override fun getDistanceToObstaclesCollision(): List<DistanceToCollision> {
        val distanceToIntersection = mutableListOf<DistanceToCollision>()
        _rayTracingConfiguration?.let { configuration ->
            _position?.let { position ->
                val obstacleList = obstacleRepositoryImpl.getAllObstacles()
                raysFactory.get(configuration, position).forEach { ray ->
                    getDistanceToAllIntersectionForRay(ray, obstacleList).also { allIntersectionDistanceForRay ->
                        getDistanceToNearestObstacle(allIntersectionDistanceForRay, configuration.maxLength).also {
                            distanceToIntersection.add(it)
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

    private fun getDistanceToAllIntersectionForRay(ray: Ray, obstaclesList: List<Obstacle>): List<Number?> {
        val allIntersectionDistanceForRay = mutableListOf<Number?>()
        obstaclesList.map { obstacle ->
            obstacle.listOfCoordinates.mapIndexed { index, point ->
                if (index + 1 < obstacle.listOfCoordinates.size) {
                    val nextPoint = obstacle.listOfCoordinates[index + 1]
                    getDistanceToIntersection(
                        ray,
                        Ray(
                            Offset(point.x.toFloat(), point.y.toFloat()),
                            Offset(
                                nextPoint.x.toFloat(),
                                nextPoint.y.toFloat()
                            )
                        )
                    ).also {
                        allIntersectionDistanceForRay.add(it)
                    }
                }
            }
        }
        return allIntersectionDistanceForRay
    }

    private fun getDistanceToNearestObstacle(
        allIntersectionDistanceForRay: List<Number?>,
        maxLength: Number
    ): DistanceToCollision {
        if (allIntersectionDistanceForRay.filterNotNull().isNotEmpty()) {
            allIntersectionDistanceForRay.filterNotNull().minBy { it.toFloat() }
                .also {
                    if (it < maxLength) return DistanceToCollision.WithinMeasurement(it)
                }
        }
        return DistanceToCollision.OutOfBound
    }
}
