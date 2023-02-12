package domain.useCase.impl

import domain.model.DetailsLidarSector
import domain.model.Ray
import domain.repository.UiRepository
import domain.useCase.GetUiRaysUseCase

internal class GetLidarUiRaysUseCaseImpl(private val uiRepository: UiRepository) : GetUiRaysUseCase {
    override fun execute(detailsLidarSector: DetailsLidarSector): List<Ray> {
        return uiRepository.getLidarUiRays(detailsLidarSector)
    }
}
