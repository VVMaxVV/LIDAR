package domain.useCase.impl

import domain.model.DistanceToCollision
import domain.repository.LidarDataRepository
import domain.useCase.GetDistanceToCollisionUseCase

internal class GetDistanceToCollisionUseCaseImpl(private val lidarDataRepository: LidarDataRepository) :
    GetDistanceToCollisionUseCase {
    override fun execute(): List<DistanceToCollision> = lidarDataRepository.getDistanceToObstaclesCollision()
}
