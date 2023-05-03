package useCase

import model.Point
import model.RayTracingConfiguration

interface GetDistanceToObstaclesUseCase {
    fun execute(
        currentPosition: Point,
        currentAngleInDegrees: Number,
        raysConfiguration: RayTracingConfiguration
    ): List<Number>
}
