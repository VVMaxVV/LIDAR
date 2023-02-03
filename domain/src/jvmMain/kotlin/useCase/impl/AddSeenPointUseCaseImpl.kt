package useCase.impl

import model.Point
import repository.PathRepository
import useCase.AddSeenPointUseCase

internal class AddSeenPointUseCaseImpl(private val pathRepository: PathRepository) : AddSeenPointUseCase {
    override suspend fun execute(points: List<Point>) {
        pathRepository.addObstacle(points)
    }
}
