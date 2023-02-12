package domain.useCase.impl

import domain.model.LidarConfiguration
import domain.model.Point
import domain.useCase.GetDistanceToObstaclesUseCase

internal class GetDistanceToObstaclesUseCaseImpl : GetDistanceToObstaclesUseCase {
    override fun execute(
        currentPosition: Point,
        currentAngleInDegrees: Number,
        raysConfiguration: LidarConfiguration
    ): List<Number> {
        return listOf()
    }
}
