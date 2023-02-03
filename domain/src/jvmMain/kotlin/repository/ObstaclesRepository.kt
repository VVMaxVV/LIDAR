package repository

import model.Line
import model.Obstacle
import model.Point

interface ObstaclesRepository {
    fun getAllObstacles(): List<Obstacle>
    fun getLinesWithinPoints(firstPoint: Point, secondPoint: Point): List<Line>
}
