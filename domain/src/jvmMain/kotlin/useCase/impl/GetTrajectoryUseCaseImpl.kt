package useCase.impl

import androidx.compose.runtime.State
import model.Point
import repository.TrajectoryRepository
import useCase.GetTrajectoryUseCase

internal class GetTrajectoryUseCaseImpl(private val trajectoryRepository: TrajectoryRepository) : GetTrajectoryUseCase {
    override fun execute(): State<List<Point>> = trajectoryRepository.getTrajectory()
}
