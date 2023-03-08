package presenter.mapper

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import domain.model.Point
import domain.utils.div
import domain.utils.minus
import domain.utils.times

internal class PointMapper {
    fun toView(point: Point, visibleHeight: Number, visibleWidth: Number, viewSize: Size): Point {
        moveOnAxisX(point, visibleWidth)
        expandOnAxisY(point, visibleHeight)
        scaleToViewSize(
            point,
            viewSize.width / visibleWidth.times(2),
            viewSize.height / visibleHeight
        )
        return point
    }

    private fun moveOnAxisX(point: Point, shift: Number) {
        point.move(Offset(x = shift.toFloat(), y = 0f))
    }

    private fun expandOnAxisY(point: Point, turningHeight: Number) {
        point.move(Offset(x = 0f, y = (turningHeight - (turningHeight - point.y).times(2)).times(-1)))
    }

    private fun scaleToViewSize(point: Point, scaleX: Number, scaleY: Number) {
        point.scale(scaleX, scaleY)
    }
}
