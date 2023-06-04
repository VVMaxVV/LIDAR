package useCase

import androidx.compose.runtime.State
import model.Point

interface GetGoalPointUseCase {
    fun execute(): State<Point?>
}
