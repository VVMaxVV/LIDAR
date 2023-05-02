package repository

import model.DistanceToCollision
import model.Position
import model.RayTracingConfiguration

interface LidarDataRepository {
    fun getDistanceToObstaclesCollision(): List<DistanceToCollision>
    fun setupConfiguration(configuration: RayTracingConfiguration)
    fun setCurrentPosition(currentPosition: Position)
}
