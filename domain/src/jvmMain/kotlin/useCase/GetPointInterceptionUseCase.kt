package useCase

import model.Point

interface GetPointInterceptionUseCase {
    fun execute(): List<Point>
}
