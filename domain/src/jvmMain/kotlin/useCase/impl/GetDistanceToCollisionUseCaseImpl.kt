package useCase.impl

import model.DistanceToCollision
import repository.LidarDataRepository
import useCase.GetDistanceToCollisionUseCase

internal class GetDistanceToCollisionUseCaseImpl(private val lidarDataRepository: LidarDataRepository) :
    GetDistanceToCollisionUseCase {
    override fun execute(): List<DistanceToCollision> = lidarDataRepository.getDistanceToObstaclesCollision()
}
