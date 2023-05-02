package data.repository

import data.factory.RaysFactory
import domain.model.Ray
import domain.model.RayTracingConfiguration
import domain.repository.UiRepository

internal class UiRepositoryImpl(private val raysFactory: RaysFactory) : UiRepository {
    override fun getLidarUiRays(rayTracingConfiguration: RayTracingConfiguration): List<Ray> =
        raysFactory.get(rayTracingConfiguration)
}
