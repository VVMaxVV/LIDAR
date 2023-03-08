package useCase.impl

import model.Position
import repository.LidarDataRepository
import useCase.SetCurrentPositionUseCase

internal class SetCurrentPositionUseCaseImpl(
    private val lidarDataRepository: LidarDataRepository
) : SetCurrentPositionUseCase {
    override fun execute(currentPosition: Position) {
        lidarDataRepository.setCurrentPosition(currentPosition)
    }
}
