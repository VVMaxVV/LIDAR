package useCase.impl

import androidx.compose.ui.geometry.Offset
import model.LineByOffset
import model.Point
import repository.ObstaclesRepository
import useCase.GetObstaclesAroundUseCase
import util.minus
import util.plus

internal class GetObstaclesAroundUseCaseImpl(private val obstaclesRepository: ObstaclesRepository) :
    GetObstaclesAroundUseCase {
    override suspend fun execute(point: Point, zoneAround: Int): List<LineByOffset> {
        return obstaclesRepository.getLinesWithinPoints(
            Point(point.x - zoneAround, point.y + zoneAround),
            Point(point.x + zoneAround, point.y - zoneAround)
        ).map {
            LineByOffset(
                Offset(it.startPoint.x.toFloat(), (it.startPoint.y.toFloat())),
                Offset(it.endPoint.x.toFloat(), (it.endPoint.y.toFloat()))
            )
        }
    }
}