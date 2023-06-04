package useCase.impl

import androidx.compose.runtime.State
import model.Position
import repository.CurrentPositionRepository
import useCase.GetCurrentPositionUseCase

internal class GetCurrentPositionUseCaseImpl(
    private val positionRepository: CurrentPositionRepository
) : GetCurrentPositionUseCase {
    override fun execute(): State<Position?> = positionRepository.getCurrentPosition()
}
