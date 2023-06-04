package useCase

import kotlinx.coroutines.flow.SharedFlow
import model.DistanceToCollision

interface GetDistanceToCollisionUseCase {
    fun execute(): SharedFlow<List<DistanceToCollision>>
}
