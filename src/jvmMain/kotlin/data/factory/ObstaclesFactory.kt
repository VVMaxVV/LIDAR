package data.factory

import domain.model.Obstacle
import domain.model.Point

class ObstaclesFactory {
    fun get(): List<Obstacle> {
        return listOf(
            Obstacle(
                listOf(
                    Point(10, 10),
                    Point(10, 20),
                    Point(20, 20),
                    Point(20, 10),
                    Point(10, 10)
                )
            )
        )
    }
}
