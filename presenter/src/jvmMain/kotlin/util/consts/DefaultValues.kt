package util.consts

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import model.Point
import model.Position
import model.TiltAngle

internal class DefaultValues {
    companion object {
        const val DEFAULT_NUMBER_OF_RAYS = 16
        const val DEFAULT_HORIZONTAL_FOV = 48
        const val DEFAULT_APPARENT_LENGTH = 45
        const val DEFAULT_VISIBLE_LENGTH = 45
        const val COLLISION_POINTS_SIZE = .5f
        const val CANVAS_VERTICAL_PADDING = 12f
        const val CANVAS_HORIZONTAL_PADDING = 12f
        const val CANVAS_TOP_MARGIN = 16
        const val CANVAS_START_MARGIN = 64
        const val NUM_VERTICAL_SCALE_LINES = 10
        const val NUM_HORIZONTAL_SCALE_LINES = 5
        const val RULER_FONT_SIZE = 16
        val startPosition = Position(Point(x = 0f, y = 0f), TiltAngle(0))
        val canvasSize = Size(400f, 420f)
        val raysColor = Color.White
        val collisionPointsColor = Color.Green
    }
}
