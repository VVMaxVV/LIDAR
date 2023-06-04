package useCase

import model.Point

interface IsMovePossibleUseCase {
    suspend fun execute(destination: Point): Boolean
}
