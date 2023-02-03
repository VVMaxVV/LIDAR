package useCase

import model.Line
import model.Point

interface GetNearestObstaclesUseCase {
    fun execute(point: Point, checkZone: Float): List<Line>
}
