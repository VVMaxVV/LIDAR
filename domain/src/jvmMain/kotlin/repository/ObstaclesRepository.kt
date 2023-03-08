package repository

import model.Obstacle

interface ObstaclesRepository {
    fun getAllObstacles(): List<Obstacle>
}
