package domain.useCase.impl

import domain.model.Point
import domain.model.RayConfiguration
import domain.useCase.GetDistanceToObstaclesUseCase

internal class GetDistanceToObstaclesUseCaseImpl : GetDistanceToObstaclesUseCase {
    override fun execute(
        currentPosition: Point,
        currentAngleInDegrees: Number,
        rayConfiguration: RayConfiguration
    ): List<Number> {
        return listOf()
    }
}