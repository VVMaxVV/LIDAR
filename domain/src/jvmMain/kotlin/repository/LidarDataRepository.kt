package repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import model.Point
import model.Ray
import model.RayTracingConfiguration
import model.sealedClass.DistanceToCollision

interface LidarDataRepository {
    fun getDistanceToObstaclesCollision(): StateFlow<List<DistanceToCollision>>

    fun getRays(): Flow<List<Ray>>

    fun setupConfiguration(configuration: RayTracingConfiguration)
    fun getPointsInterception(): List<Point>
}
