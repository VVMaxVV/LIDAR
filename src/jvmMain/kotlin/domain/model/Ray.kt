package domain.model

import androidx.compose.ui.geometry.Offset
import domain.utils.move
import domain.utils.scale

data class Ray(var start: Offset, var end: Offset) {
    fun moveStart(offsetStart: Offset) = this.apply {
        start = this.start.move(offsetStart.x, offsetStart.y)
    }

    fun moveEnd(offsetEnd: Offset) = this.apply {
        end = this.end.move(offsetEnd.x, offsetEnd.y)
    }

    fun scaleStart(scaleX: Number = 1, scaleY: Number = 1) = this.apply {
        start = this.start.scale(scaleX, scaleY)
    }

    fun scaleEnd(scaleX: Number = 1, scaleY: Number = 1) = this.apply {
        end = this.end.scale(scaleX, scaleY)
    }
}
