package domain.useCase.impl

import domain.model.LidarConfiguration
import domain.model.Ray
import domain.repository.UiRepository
import domain.useCase.GetUiRaysUseCase

internal class GetLidarUiRaysUseCaseImpl(private val uiRepository: UiRepository) : GetUiRaysUseCase {
    override fun execute(lidarConfiguration: LidarConfiguration): List<Ray> {
        return uiRepository.getLidarUiRays(lidarConfiguration)
    }
}
