package domain.useCase

import domain.model.Point
import domain.model.RaysConfiguration

interface GetDistanceToObstaclesUseCase {
    fun execute(
        currentPosition: Point,
        currentAngleInDegrees: Number,
        raysConfiguration: RaysConfiguration
    ): List<Number>
}
