package useCase.impl

import model.Line
import model.Point
import repository.ObstaclesRepository
import useCase.GetNearestObstaclesUseCase
import util.minus
import util.plus

internal class GetNearestObstaclesUseCaseImpl(
    private val obstaclesRepository: ObstaclesRepository
) : GetNearestObstaclesUseCase {
    override fun execute(point: Point, checkZone: Float): List<Line> {
        return obstaclesRepository.getLinesWithinPoints(
            Point(point.x + checkZone, point.y + checkZone),
            Point(point.x - checkZone, point.y - checkZone)
        )
    }
}
