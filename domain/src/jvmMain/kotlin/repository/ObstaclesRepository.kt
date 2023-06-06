package repository

import model.Line
import model.Obstacle
import model.Point
import model.sealedClass.LocationSample

interface ObstaclesRepository {
    fun getAllObstacles(): List<Obstacle>
    fun getLinesWithinPoints(firstPoint: Point, secondPoint: Point): List<Line>
    fun setLocationSample(locationSample: LocationSample)
}
