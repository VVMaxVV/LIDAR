package presenter.mapper

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import domain.model.Ray
import domain.utils.minus

internal class RayMapper {
    fun toCanvasLines(rayList: List<Ray>, size: Size): List<Ray> {
        val offsetX = rayList.maxWith(Comparator.comparing { it.end.x.toDouble() }).end.x
        val offsetY = rayList.maxWith(Comparator.comparing { it.end.y.toDouble() }).end.y
        rayList.map {
            moveOnAxisX(it, offsetX)
            expandOnAxisY(it, offsetY)
        }
        return scaleToViewSize(rayList, size)
    }

    private fun scaleToViewSize(rayList: List<Ray>, size: Size): List<Ray> {
        val offsetX = rayList.maxWith(Comparator.comparing { it.end.x.toDouble() }).end.x
        val offsetY = rayList.maxWith(Comparator.comparing { it.start.y.toDouble() }).start.y
        val scaleX = size.width / offsetX
        val scaleY = size.height / offsetY
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
