package domain.useCase

import domain.model.LidarConfiguration
import domain.model.Ray

interface GetUiRaysUseCase {
    fun execute(lidarConfiguration: LidarConfiguration): List<Ray>
}
