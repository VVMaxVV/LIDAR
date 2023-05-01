package useCase.impl

import model.Point
import repository.PathRepository
import useCase.GetPathUseCase

internal class GetPathUseCaseImpl(private val pathRepository: PathRepository) : GetPathUseCase {
    override suspend fun execute(start: Point, goal: Point) = pathRepository.getPath(start, goal)
}
