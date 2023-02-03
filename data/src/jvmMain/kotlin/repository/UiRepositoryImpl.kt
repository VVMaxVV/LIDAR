package repository

import factory.RaysFactory
import model.Ray
import model.RayTracingConfiguration

internal class UiRepositoryImpl(private val raysFactory: RaysFactory) : UiRepository {
    override fun getLidarUiRays(rayTracingConfiguration: RayTracingConfiguration): List<Ray> =
        raysFactory.get(rayTracingConfiguration)
}
