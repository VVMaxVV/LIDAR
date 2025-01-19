package useCase

import kotlinx.coroutines.flow.StateFlow
import model.Point

interface GetTrajectoryUseCase {
    fun execute(): StateFlow<List<Point>>
}
