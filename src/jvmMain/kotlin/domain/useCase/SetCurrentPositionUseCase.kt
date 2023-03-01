package domain.useCase

import domain.model.Position

interface SetCurrentPositionUseCase {
    fun execute(currentPosition: Position)
}
