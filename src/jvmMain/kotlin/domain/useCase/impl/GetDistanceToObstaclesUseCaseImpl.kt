package domain.useCase.impl

import domain.model.Point
import domain.model.RaysConfiguration
import domain.useCase.GetDistanceToObstaclesUseCase

internal class GetDistanceToObstaclesUseCaseImpl : GetDistanceToObstaclesUseCase {
    override fun execute(
        currentPosition: Point,
        currentAngleInDegrees: Number,
        raysConfiguration: RaysConfiguration
    ): List<Number> {
        return listOf()
    }
}
