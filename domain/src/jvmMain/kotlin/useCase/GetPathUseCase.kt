package useCase

import model.Point

interface GetPathUseCase {
    fun execute(start: Point, goal: Point): List<Point>
}
