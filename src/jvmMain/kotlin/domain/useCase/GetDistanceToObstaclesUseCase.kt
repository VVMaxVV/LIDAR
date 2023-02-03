package domain.useCase

import domain.model.Point
import domain.model.RayConfiguration

interface GetDistanceToObstaclesUseCase {
    fun execute(currentPosition: Point, currentAngleInDegrees: Number, rayConfiguration: RayConfiguration): List<Number>
}
