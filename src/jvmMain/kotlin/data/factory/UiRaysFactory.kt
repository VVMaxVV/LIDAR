package data.factory

import androidx.compose.ui.geometry.Offset
import domain.model.DetailsLidarSector
import domain.model.Ray
import domain.utils.div
import domain.utils.toRadians
import kotlin.math.cos
import kotlin.math.sin

internal class UiRaysFactory {
    fun get(detailsLidarSector: DetailsLidarSector): List<Ray> {
        val coordinatesList = mutableListOf<Ray>()
        val startDestinationDegree =
            detailsLidarSector.horizontalFov / 2 - detailsLidarSector.horizontalFov / (detailsLidarSector.numbersOfRay * 2)
        for (rayNumber in 0 until detailsLidarSector.numbersOfRay / 2) {
            coordinatesList.add(
                Ray(
                    start = Offset(0f, 0f),
                    end = Offset(
                        getX((90 - startDestinationDegree) + 3 * rayNumber, detailsLidarSector.maxLength).toFloat(),
                        getY((90 - startDestinationDegree) + 3 * rayNumber, detailsLidarSector.maxLength).toFloat()
                    )
                )
            )
        }
        coordinatesList.take(detailsLidarSector.numbersOfRay / 2).asReversed().map {
            coordinatesList.add(
                Ray(
                    start = Offset(
                        it.start.x,
                        it.start.y
                    ),
                    end = Offset(
                        x = it.end.x.times(-1),
                        y = it.end.y
                    )
                )
            )
        }
        return coordinatesList
    }

    private fun getX(degree: Number, rayLength: Number) = cos(degree.toRadians()) * rayLength.toDouble()

    private fun getY(degree: Number, rayLength: Number) = sin(degree.toRadians()) * rayLength.toDouble()
}
