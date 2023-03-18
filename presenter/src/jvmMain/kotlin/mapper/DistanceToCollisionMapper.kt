package mapper

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import model.DistanceToCollision
import model.Point
import model.RayTracingConfiguration
import util.div
import util.getX
import util.getY

class DistanceToCollisionMapper(private val pointMapper: PointMapper) {
    fun toOffsetsOnView(
        distanceToCollisionList: List<DistanceToCollision>,
        maxLengthVisibility: Number,
        rayTracingConfiguration: RayTracingConfiguration,
        viewSize: Size
    ): List<Offset> {
        val offsetList = mutableListOf<Offset>()
        val startDestinationDegree =
            rayTracingConfiguration.horizontalFov / 2 - rayTracingConfiguration.horizontalFov /
                (rayTracingConfiguration.numbersOfRay * 2)
        val degreeDivision = rayTracingConfiguration.horizontalFov / rayTracingConfiguration.numbersOfRay
        val maxLateralDeviation = getX(maxLengthVisibility, 90 - rayTracingConfiguration.horizontalFov / 2)
        distanceToCollisionList.mapIndexed { index, distance ->
            (distance as? DistanceToCollision.WithinMeasurement)?.also {
                pointMapper.toView(
                    Point(
                        getX(it.distance, (90 - startDestinationDegree) + degreeDivision * index),
                        getY(it.distance, (90 - startDestinationDegree) + degreeDivision * index)
                    ),
                    maxLengthVisibility,
                    maxLateralDeviation,
                    viewSize
                ).also {
                    offsetList.add(Offset(it.x.toFloat(), it.y.toFloat()))
                }
            }
        }
        return offsetList
    }
}
