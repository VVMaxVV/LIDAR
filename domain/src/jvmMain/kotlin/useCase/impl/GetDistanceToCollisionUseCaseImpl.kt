package useCase.impl

import kotlinx.coroutines.flow.SharedFlow
import model.DistanceToCollision
import repository.LidarDataRepository
import useCase.GetDistanceToCollisionUseCase

internal class GetDistanceToCollisionUseCaseImpl(private val lidarDataRepository: LidarDataRepository) :
    GetDistanceToCollisionUseCase {
    override fun execute(): SharedFlow<List<DistanceToCollision>> = lidarDataRepository.getDistanceToObstaclesCollision()
}
