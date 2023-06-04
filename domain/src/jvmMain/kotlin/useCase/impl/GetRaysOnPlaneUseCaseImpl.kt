package useCase.impl

import kotlinx.coroutines.flow.Flow
import model.Ray
import repository.LidarDataRepository
import useCase.GetRaysOnPlaneUseCase

internal class GetRaysOnPlaneUseCaseImpl(private val lidarDataRepository: LidarDataRepository) : GetRaysOnPlaneUseCase {
    override fun execute(): Flow<List<Ray>> = lidarDataRepository.getRays()
}
