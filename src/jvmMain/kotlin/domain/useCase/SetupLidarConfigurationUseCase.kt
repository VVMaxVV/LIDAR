package domain.useCase

import domain.model.Position
import domain.model.RayTracingConfiguration

interface SetupLidarConfigurationUseCase {
    fun execute(rayTracingConfiguration: RayTracingConfiguration, currentPosition: Position? = null)
}
