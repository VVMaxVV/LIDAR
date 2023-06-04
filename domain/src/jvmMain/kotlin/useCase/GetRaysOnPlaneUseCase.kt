package useCase

import kotlinx.coroutines.flow.Flow
import model.Ray

interface GetRaysOnPlaneUseCase {
    fun execute(): Flow<List<Ray>>
}
