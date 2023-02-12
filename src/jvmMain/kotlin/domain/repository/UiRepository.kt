package domain.repository

import domain.model.DetailsLidarSector
import domain.model.Ray

interface UiRepository {
    fun getLidarUiRays(detailsLidarSector: DetailsLidarSector): List<Ray>
}
