package useCase

import androidx.compose.runtime.State
import model.Point

interface GetTrajectoryUseCase {
    fun execute(): State<List<Point>>
}
