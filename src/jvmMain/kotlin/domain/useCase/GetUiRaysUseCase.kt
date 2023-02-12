package domain.useCase

import domain.model.DetailsLidarSector
import domain.model.Ray

interface GetUiRaysUseCase {
    fun execute(detailsLidarSector: DetailsLidarSector): List<Ray>
}
