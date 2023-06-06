package useCase

import model.sealedClass.Movements

interface MoveUseCase {
    suspend fun execute(movements: Movements)
}
