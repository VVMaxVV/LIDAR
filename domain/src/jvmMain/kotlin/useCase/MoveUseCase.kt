package useCase

import model.Movements

interface MoveUseCase {
    suspend fun execute(movements: Movements)
}
