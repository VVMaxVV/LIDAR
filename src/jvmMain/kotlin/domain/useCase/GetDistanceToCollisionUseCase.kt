package domain.useCase

import domain.model.DistanceToCollision

interface GetDistanceToCollisionUseCase {
    fun execute(): List<DistanceToCollision>
}
