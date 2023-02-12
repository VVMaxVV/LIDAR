package domain.repository

import domain.model.LidarConfiguration
import domain.model.Ray

interface UiRepository {
    fun getLidarUiRays(lidarConfiguration: LidarConfiguration): List<Ray>
}
