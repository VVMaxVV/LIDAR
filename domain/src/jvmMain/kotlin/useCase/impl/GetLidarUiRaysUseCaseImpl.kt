package useCase.impl

import model.Ray
import model.RayTracingConfiguration
import repository.UiRepository
import useCase.GetUiRaysUseCase

internal class GetLidarUiRaysUseCaseImpl(private val uiRepository: UiRepository) : GetUiRaysUseCase {
    override fun execute(rayTracingConfiguration: RayTracingConfiguration): List<Ray> {
        return uiRepository.getLidarUiRays(rayTracingConfiguration)
    }
}
