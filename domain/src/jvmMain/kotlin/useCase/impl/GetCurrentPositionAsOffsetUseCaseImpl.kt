package useCase.impl

import androidx.compose.ui.geometry.Offset
import repository.CurrentPositionRepository
import useCase.GetCurrentPositionAsOffsetUseCase

internal class GetCurrentPositionAsOffsetUseCaseImpl(
    private val currentPositionRepository: CurrentPositionRepository
) :
    GetCurrentPositionAsOffsetUseCase {
    override fun execute(): Offset? {
        return currentPositionRepository.getCurrentPosition().value?.currentCoordinates?.let {
            Offset(it.x.toFloat(), it.y.toFloat())
        }
    }
}
