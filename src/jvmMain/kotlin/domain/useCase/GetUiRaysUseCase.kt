package domain.useCase

import domain.model.Ray
import domain.model.RayTracingConfiguration

interface GetUiRaysUseCase {
    fun execute(rayTracingConfiguration: RayTracingConfiguration): List<Ray>
}
