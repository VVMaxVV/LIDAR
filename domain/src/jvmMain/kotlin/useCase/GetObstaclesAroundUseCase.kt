package useCase

import model.LineByOffset

interface GetObstaclesAroundUseCase {
    suspend fun execute(zoneAround: Int): List<LineByOffset>
}
