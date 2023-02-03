package domain.repository

import domain.model.Obstacle

interface ObstaclesRepository {
    fun getAllObstacles(): List<Obstacle>
}
