package useCase

import model.Position

interface SetCurrentPositionUseCase {
    fun execute(currentPosition: Position)
}
