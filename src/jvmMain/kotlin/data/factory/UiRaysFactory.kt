package data.factory

import androidx.compose.ui.geometry.Offset
import domain.model.LidarConfiguration
import domain.model.Ray
import domain.utils.div
import domain.utils.toRadians
import kotlin.math.cos
import kotlin.math.sin

internal class UiRaysFactory {
    fun get(lidarConfiguration: LidarConfiguration): List<Ray> {
        val coordinatesList = mutableListOf<Ray>()
        val startDestinationDegree =
            lidarConfiguration.horizontalFov / 2 - lidarConfiguration.horizontalFov /
                    (lidarConfiguration.numbersOfRay * 2)
        val degreeDivision = lidarConfiguration.horizontalFov / lidarConfiguration.numbersOfRay
        for (rayNumber in 0 until lidarConfiguration.numbersOfRay) {
            coordinatesList.add(
                Ray(
                    start = Offset(0f, 0f),
                    end = Offset(
                        getX(
                            (90 - startDestinationDegree) + degreeDivision * rayNumber,
                            lidarConfiguration.maxLength
                        ).toFloat(),
                        getY(
                            (90 - startDestinationDegree) + degreeDivision * rayNumber,
                            lidarConfiguration.maxLength
                        ).toFloat()
                    )
                )
            )
        }
        return coordinatesList
    }

    private fun getX(degree: Number, rayLength: Number) = cos(degree.toRadians()) * rayLength.toDouble()

    private fun getY(degree: Number, rayLength: Number) = sin(degree.toRadians()) * rayLength.toDouble()
}
