package useCase

import model.RayTracingConfiguration

interface SetupRayTracingConfigurationUseCase {
    fun execute(rayTracingConfiguration: RayTracingConfiguration)
}
