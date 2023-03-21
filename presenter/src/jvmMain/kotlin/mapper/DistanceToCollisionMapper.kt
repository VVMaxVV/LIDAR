package mapper

import androidx.compose.ui.geometry.Offset
import model.DistanceToCollision
import model.RayTracingConfiguration
import util.compareTo
import util.div
import util.getX
import util.getY

internal class DistanceToCollisionMapper {
    fun toOffsetsOnView(
        distanceToCollisionList: List<DistanceToCollision>,
        maxLengthVisibility: Number,
        rayTracingConfiguration: RayTracingConfiguration
    ): List<Offset> {
        val offsetList = mutableListOf<Offset>()
        val startDestinationDegree =
            rayTracingConfiguration.horizontalFov / 2
        val degreeDivision = rayTracingConfiguration.horizontalFov / (rayTracingConfiguration.numbersOfRay - 1)
        distanceToCollisionList.mapIndexed { index, distance ->
            (distance as? DistanceToCollision.WithinMeasurement)?.also {
                if (it.distance < maxLengthVisibility) {
                    offsetList.add(
                        Offset(
                            getX(it.distance, (90 - startDestinationDegree) + degreeDivision * index),
                            getY(it.distance, (90 - startDestinationDegree) + degreeDivision * index)
                        )
                    )
                }
            }
        }
        return offsetList
    }
}
