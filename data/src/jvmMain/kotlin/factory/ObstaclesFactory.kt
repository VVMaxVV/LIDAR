package factory

import model.Obstacle
import model.Point

internal class ObstaclesFactory {
    fun get(): List<Obstacle> = listOf(
        Obstacle(
            listOf(
                Point(-10, 10),
                Point(-10, 20),
                Point(10, 20),
                Point(10, 10),
                Point(-10, 10)
            )
        )
    )
}
