package useCase.impl

import model.sealedClass.LocationSample
import repository.ObstaclesRepository
import repository.PathRepository
import useCase.SetSampleLocationUseCase

internal class SetSampleLocationUseCaseImpl(
    private val obstaclesRepository: ObstaclesRepository,
    private val pathRepository: PathRepository
) :
    SetSampleLocationUseCase {
    override suspend fun execute(locationSample: LocationSample) {
        obstaclesRepository.setLocationSample(locationSample)
        pathRepository.clearMemorySpace()
    }
}
