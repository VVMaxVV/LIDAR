package useCase

import model.Ray
import model.RayTracingConfiguration

interface GetUiRaysUseCase {
    fun execute(rayTracingConfiguration: RayTracingConfiguration): List<Ray>
}
