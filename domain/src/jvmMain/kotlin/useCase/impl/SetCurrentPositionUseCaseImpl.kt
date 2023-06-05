package useCase.impl

import model.Position
import repository.CurrentPositionRepository
import useCase.SetCurrentPositionUseCase

internal class SetCurrentPositionUseCaseImpl(
    private val currentPositionRepository: CurrentPositionRepository
) : SetCurrentPositionUseCase {
    override suspend fun execute(currentPosition: Position) {
        currentPositionRepository.setCurrentPosition(currentPosition)
    }
}
