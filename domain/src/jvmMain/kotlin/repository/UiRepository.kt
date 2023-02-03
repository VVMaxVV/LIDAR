package repository

import model.Ray
import model.RayTracingConfiguration

interface UiRepository {
    fun getLidarUiRays(rayTracingConfiguration: RayTracingConfiguration): List<Ray>
}
