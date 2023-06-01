package useCase

import model.LineByOffset
import model.Point

interface GetObstaclesAroundUseCase {
    suspend fun execute(point: Point, zoneAround: Int): List<LineByOffset>
}