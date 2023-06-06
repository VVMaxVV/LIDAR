package useCase

import kotlinx.coroutines.flow.SharedFlow
import model.sealedClass.DistanceToCollision

interface GetDistanceToCollisionUseCase {
    fun execute(): SharedFlow<List<DistanceToCollision>>
}
