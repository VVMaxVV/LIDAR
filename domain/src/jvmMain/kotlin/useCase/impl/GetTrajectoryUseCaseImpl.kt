package useCase.impl

import kotlinx.coroutines.flow.StateFlow
import model.Point
import repository.TrajectoryRepository
import useCase.GetTrajectoryUseCase

internal class GetTrajectoryUseCaseImpl(private val trajectoryRepository: TrajectoryRepository) : GetTrajectoryUseCase {
    override fun execute(): StateFlow<List<Point>> = trajectoryRepository.getTrajectory()
}
