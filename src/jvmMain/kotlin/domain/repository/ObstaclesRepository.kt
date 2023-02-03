package domain.repository

import domain.model.Obstacles

interface ObstaclesRepository {
    fun getAllObstacles(): List<Obstacles>
}
