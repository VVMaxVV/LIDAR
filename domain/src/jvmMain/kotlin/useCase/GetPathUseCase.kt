package useCase

import model.Point

interface GetPathUseCase {
    suspend fun execute(start: Point, goal: Point): List<Point>
}
