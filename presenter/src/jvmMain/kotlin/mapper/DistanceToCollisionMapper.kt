package mapper

import androidx.compose.ui.geometry.Offset
import model.RayTracingConfiguration
import model.sealedClass.DistanceToCollision
import util.compareTo
import util.div
import util.getX
import util.getY

internal class DistanceToCollisionMapper {
    fun toOffsetsOnView(
        distanceToCollisionList: List<DistanceToCollision>,
        maxLengthVisibility: Number,
        rayTracingConfiguration: RayTracingConfiguration
    ): List<Pair<Offset, Offset>> {
        val offsetList = mutableListOf<Pair<Offset, Offset>>()
        val startDestinationDegree =
            rayTracingConfiguration.horizontalFov / 2
        val degreeDivision = rayTracingConfiguration.horizontalFov / (rayTracingConfiguration.numbersOfRay - 1)
        distanceToCollisionList.mapIndexed { index, distance ->
            (distance as? DistanceToCollision.WithinMeasurement)?.also {
                if (it.distance < maxLengthVisibility) {
                    offsetList.add(
                        Pair(
                            Offset(
                                getX(
                                    it.distance,
                                    (90 - startDestinationDegree) + degreeDivision * index + degreeDivision
                                ),
                                getY(
                                    it.distance,
                                    (90 - startDestinationDegree) + degreeDivision * index + degreeDivision
                                )
                            ),
                            Offset(
                                getX(
                                    it.distance,
                                    (90 - startDestinationDegree) + degreeDivision * index
                                ),
                                getY(
                                    it.distance,
                                    (90 - startDestinationDegree) + degreeDivision * index
                                )
                            )
                        )
                    )
                }
            }
        }
        return offsetList
    }
}
