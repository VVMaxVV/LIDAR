package util.consts

import androidx.compose.ui.graphics.Color
import model.Point
import model.Position
import model.TiltAngle

internal class DefaultValues {
    companion object {
        const val DEFAULT_NUMBER_OF_RAYS = 16
        const val DEFAULT_HORIZONTAL_FOV = 48
        const val DEFAULT_APPARENT_RAY_LENGTH = 45
        const val DEFAULT_MEASURE_RAY_LENGTH = 50
        const val COLLISION_POINTS_SIZE = .5f
        const val NUM_VERTICAL_SCALE_LINES = 10
        const val NUM_HORIZONTAL_SCALE_LINES = 5
        val startPosition = Position(Point(x = 0f, y = 0f), TiltAngle(0))
        val raysColor = Color.White
        val collisionPointsColor = Color.Green
    }
}
