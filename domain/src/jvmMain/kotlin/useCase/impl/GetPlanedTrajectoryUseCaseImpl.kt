package useCase.impl

import kotlinx.coroutines.flow.StateFlow
import model.Point
import repository.PathRepository
import useCase.GetPlanedTrajectoryUseCase

internal class GetPlanedTrajectoryUseCaseImpl(
    private val pathRepository: PathRepository
): GetPlanedTrajectoryUseCase {
    override fun execute(): StateFlow<List<Point>> {
        return pathRepository.getStoredPath()
    }
}
