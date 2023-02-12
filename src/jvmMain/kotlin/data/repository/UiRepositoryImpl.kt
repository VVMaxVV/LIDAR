package data.repository

import data.factory.UiRaysFactory
import domain.model.DetailsLidarSector
import domain.model.Ray
import domain.repository.UiRepository

internal class UiRepositoryImpl(private val raysFactory: UiRaysFactory) : UiRepository {
    override fun getLidarUiRays(detailsLidarSector: DetailsLidarSector): List<Ray> = raysFactory.get(detailsLidarSector)
}
