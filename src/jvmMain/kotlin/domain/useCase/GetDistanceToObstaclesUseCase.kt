package domain.useCase

import domain.model.LidarConfiguration
import domain.model.Point

interface GetDistanceToObstaclesUseCase {
    fun execute(
        currentPosition: Point,
        currentAngleInDegrees: Number,
        raysConfiguration: LidarConfiguration
    ): List<Number>
}
