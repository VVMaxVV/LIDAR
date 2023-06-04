package useCase.impl

import androidx.compose.ui.geometry.Offset
import model.LineByOffset
import model.Point
import repository.CurrentPositionRepository
import repository.ObstaclesRepository
import useCase.GetObstaclesAroundUseCase
import util.minus
import util.plus

internal class GetObstaclesAroundUseCaseImpl(
    private val obstaclesRepository: ObstaclesRepository,
    private val currentPositionRepository: CurrentPositionRepository
) : GetObstaclesAroundUseCase {
    override suspend fun execute(zoneAround: Int): List<LineByOffset> {
        return currentPositionRepository.getCurrentPosition().value?.currentCoordinates?.let { point ->
            return@let obstaclesRepository.getLinesWithinPoints(
                Point(point.x - zoneAround, point.y + zoneAround),
                Point(point.x + zoneAround, point.y - zoneAround)
            ).map {
                LineByOffset(
                    Offset(it.startPoint.x.toFloat(), (it.startPoint.y.toFloat())),
                    Offset(it.endPoint.x.toFloat(), (it.endPoint.y.toFloat()))
                )
            }
        } ?: emptyList()
    }
}
