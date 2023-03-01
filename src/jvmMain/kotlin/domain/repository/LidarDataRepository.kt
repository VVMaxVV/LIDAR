package domain.repository

import domain.model.DistanceToCollision
import domain.model.Position
import domain.model.RayTracingConfiguration

interface LidarDataRepository {
    fun getDistanceToObstaclesCollision(): List<DistanceToCollision>
    fun setupConfiguration(configuration: RayTracingConfiguration)
    fun setCurrentPosition(currentPosition: Position)
}
