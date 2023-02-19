package domain.useCase

import domain.model.Position
import domain.model.RayTracingConfiguration

interface SetupRayTracingConfigurationUseCase {
    fun execute(rayTracingConfiguration: RayTracingConfiguration, currentPosition: Position? = null)
}
