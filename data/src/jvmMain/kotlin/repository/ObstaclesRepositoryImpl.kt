package repository

import factory.ObstaclesFactory
import model.Obstacle

internal class ObstaclesRepositoryImpl(private val obstaclesFactory: ObstaclesFactory) : ObstaclesRepository {
    override fun getAllObstacles(): List<Obstacle> = obstaclesFactory.get()
}
