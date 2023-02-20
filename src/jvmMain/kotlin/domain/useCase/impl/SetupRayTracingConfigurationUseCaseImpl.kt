package domain.useCase.impl

import domain.model.RayTracingConfiguration
import domain.repository.LidarDataRepository
import domain.useCase.SetupRayTracingConfigurationUseCase

internal class SetupRayTracingConfigurationUseCaseImpl(private val lidarDataRepository: LidarDataRepository) :
    SetupRayTracingConfigurationUseCase {
    override fun execute(rayTracingConfiguration: RayTracingConfiguration) {
        lidarDataRepository.setupConfiguration(rayTracingConfiguration)
    }
}
