package domain.useCase.impl

import domain.model.Position
import domain.model.RayTracingConfiguration
import domain.repository.LidarDataRepository
import domain.useCase.SetupRayTracingConfigurationUseCase

internal class SetupRayTracingConfigurationUseCaseImpl(private val lidarDataRepository: LidarDataRepository) :
    SetupRayTracingConfigurationUseCase {
    override fun execute(rayTracingConfiguration: RayTracingConfiguration, currentPosition: Position?) {
        lidarDataRepository.setupConfiguration(rayTracingConfiguration)
        currentPosition?.let { lidarDataRepository.setCurrentPosition(it) }
    }
}
