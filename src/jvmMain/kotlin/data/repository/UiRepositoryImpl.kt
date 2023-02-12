package data.repository

import data.factory.UiRaysFactory
import domain.model.LidarConfiguration
import domain.model.Ray
import domain.repository.UiRepository

internal class UiRepositoryImpl(private val raysFactory: UiRaysFactory) : UiRepository {
    override fun getLidarUiRays(lidarConfiguration: LidarConfiguration): List<Ray> = raysFactory.get(lidarConfiguration)
}
