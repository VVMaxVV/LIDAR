package data.repository

import data.factory.ObstaclesFactory
import domain.model.Obstacle
import domain.repository.ObstaclesRepository

class ObstaclesRepositoryImpl(private val obstaclesFactory: ObstaclesFactory) : ObstaclesRepository {
    override fun getAllObstacles(): List<Obstacle> = obstaclesFactory.get()
}
