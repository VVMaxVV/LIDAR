package repository

import factory.ObstaclesFactory
import model.Obstacle
import model.Point
import model.sealedClass.LocationSample

internal class ObstaclesRepositoryImpl(private val obstaclesFactory: ObstaclesFactory) : ObstaclesRepository {
    override fun getAllObstacles(): List<Obstacle> = obstaclesFactory.getAll()

    override fun getLinesWithinPoints(firstPoint: Point, secondPoint: Point) =
        obstaclesFactory.get(firstPoint, secondPoint)

    override fun setLocationSample(locationSample: LocationSample) {
        obstaclesFactory.changeLocation(locationSample)
    }
}
