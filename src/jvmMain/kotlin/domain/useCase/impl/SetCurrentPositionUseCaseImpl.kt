package domain.useCase.impl

import domain.model.Position
import domain.repository.LidarDataRepository
import domain.useCase.SetCurrentPositionUseCase

internal class SetCurrentPositionUseCaseImpl(
    private val lidarDataRepository: LidarDataRepository
) : SetCurrentPositionUseCase {
    override fun execute(currentPosition: Position) {
        lidarDataRepository.setCurrentPosition(currentPosition)
    }
}
