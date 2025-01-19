package useCase

import kotlinx.coroutines.flow.StateFlow
import model.Point

interface GetPlanedTrajectoryUseCase {
    fun execute(): StateFlow<List<Point>>
}
