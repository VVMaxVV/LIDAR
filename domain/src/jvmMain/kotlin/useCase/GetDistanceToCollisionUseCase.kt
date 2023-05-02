package useCase

import model.DistanceToCollision

interface GetDistanceToCollisionUseCase {
    fun execute(): List<DistanceToCollision>
}
