package useCase

import model.Position

interface SetCurrentPositionUseCase {
    suspend fun execute(currentPosition: Position)
}
