package useCase

import androidx.compose.runtime.State
import model.Position

interface GetCurrentPositionUseCase {
    fun execute(): State<Position?>
}
