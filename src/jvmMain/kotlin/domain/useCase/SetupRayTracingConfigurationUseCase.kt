package domain.useCase

import domain.model.RayTracingConfiguration

interface SetupRayTracingConfigurationUseCase {
    fun execute(rayTracingConfiguration: RayTracingConfiguration)
}
