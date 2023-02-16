package presenter.mapper

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import domain.model.Ray
import domain.utils.div
import domain.utils.minus
import domain.utils.times

internal class RayMapper {
    fun toView(rayList: List<Ray>, visibleHeight: Number, visibleWidth: Number, viewSize: Size): List<Ray> {
        rayList.map { ray ->
            moveOnAxisX(ray, visibleWidth)
            expandOnAxisY(ray, visibleHeight)
        }

        return scaleToViewSize(
            rayList,
            viewSize.width / visibleWidth.times(2),
            viewSize.height / visibleHeight
        )
    }

    private fun scaleToViewSize(rayList: List<Ray>, scaleX: Number, scaleY: Number): List<Ray> {
        return rayList.map { ray ->
            ray.scaleStart(scaleX, scaleY)
            ray.scaleEnd(scaleX, scaleY)
        }
    }

    private fun expandOnAxisY(ray: Ray, turningHeight: Number) {
        ray.moveStart(Offset(x = 0f, y = turningHeight.toFloat()))
        ray.moveEnd(Offset(x = 0f, y = (turningHeight - (turningHeight - ray.end.y).times(2)).times(-1)))
    }

    private fun moveOnAxisX(ray: Ray, shift: Number) {
        ray.moveStart(Offset(x = shift.toFloat(), y = 0f))
        ray.moveEnd(Offset(x = shift.toFloat(), y = 0f))
    }
}
