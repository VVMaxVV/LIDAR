package useCase.impl

import model.RayTracingConfiguration
import repository.LidarDataRepository
import useCase.SetupRayTracingConfigurationUseCase

internal class SetupRayTracingConfigurationUseCaseImpl(private val lidarDataRepository: LidarDataRepository) :
    SetupRayTracingConfigurationUseCase {
    override fun execute(rayTracingConfiguration: RayTracingConfiguration) {
        lidarDataRepository.setupConfiguration(rayTracingConfiguration)
    }
}
