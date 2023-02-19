package domain.repository

import domain.model.Ray
import domain.model.RayTracingConfiguration

interface UiRepository {
    fun getLidarUiRays(rayTracingConfiguration: RayTracingConfiguration): List<Ray>
}
