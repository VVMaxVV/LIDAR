package domain.useCase.impl

import domain.model.Ray
import domain.model.RayTracingConfiguration
import domain.repository.UiRepository
import domain.useCase.GetUiRaysUseCase

internal class GetLidarUiRaysUseCaseImpl(private val uiRepository: UiRepository) : GetUiRaysUseCase {
    override fun execute(rayTracingConfiguration: RayTracingConfiguration): List<Ray> {
        return uiRepository.getLidarUiRays(rayTracingConfiguration)
    }
}
