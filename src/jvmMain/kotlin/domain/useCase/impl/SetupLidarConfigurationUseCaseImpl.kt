package domain.useCase.impl

import domain.model.Position
import domain.model.RayTracingConfiguration
import domain.repository.LidarDataRepository
import domain.useCase.SetupLidarConfigurationUseCase

internal class SetupLidarConfigurationUseCaseImpl(private val lidarDataRepository: LidarDataRepository) :
    SetupLidarConfigurationUseCase {
    override fun execute(rayTracingConfiguration: RayTracingConfiguration, currentPosition: Position?) {
        lidarDataRepository.setupConfiguration(rayTracingConfiguration)
        currentPosition?.let { lidarDataRepository.setCurrentPosition(it) }
    }
}
