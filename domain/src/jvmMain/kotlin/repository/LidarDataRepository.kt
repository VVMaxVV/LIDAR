package repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import model.DistanceToCollision
import model.Point
import model.Ray
import model.RayTracingConfiguration

interface LidarDataRepository {
    fun getDistanceToObstaclesCollision(): SharedFlow<List<DistanceToCollision>>

    fun getRays(): Flow<List<Ray>>

    fun setupConfiguration(configuration: RayTracingConfiguration)
    fun getPointsInterception(): List<Point>
}
