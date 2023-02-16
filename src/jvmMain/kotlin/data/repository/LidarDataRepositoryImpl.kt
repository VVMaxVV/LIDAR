package data.repository

import androidx.compose.ui.geometry.Offset
import data.factory.RaysFactory
import domain.model.DistanceToCollision
import domain.model.Position
import domain.model.Ray
import domain.model.RayTracingConfiguration
import domain.repository.LidarDataRepository
import domain.repository.ObstaclesRepository
import domain.utils.compareTo
import domain.utils.getDistanceToIntersection


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
                    val allIntersectionDistanceForRay = mutableListOf<Number?>()
                    obstacleList.map { obstacle ->
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
                    if (allIntersectionDistanceForRay.filterNotNull().isNotEmpty())
                        allIntersectionDistanceForRay.filterNotNull().minBy { it.toFloat() }.also {
                            if (it < configuration.maxLength) distanceToIntersection.add(
                                DistanceToCollision.WithinMeasurement(
                                    it
                                )
                            )
                            else distanceToIntersection.add(DistanceToCollision.OutOfBound)
                        }
                    else distanceToIntersection.add(DistanceToCollision.OutOfBound)
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
}
