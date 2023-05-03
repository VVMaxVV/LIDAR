package useCase

import model.Point

interface AddSeenPointUseCase {
    suspend fun execute(points: List<Point>)
}
