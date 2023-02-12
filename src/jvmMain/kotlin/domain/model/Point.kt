package domain.model

import androidx.compose.ui.geometry.Offset
import domain.utils.plus
import domain.utils.times

data class Point(var x: Number = 0, var y: Number = 0) {
    fun move(offset: Offset) = this.also {
        x += offset.x
        y += offset.y
    }

    fun scale(scaleX: Number = 1, scaleY: Number = 1, scaleZ: Number = 1) = this.also {
        x *= scaleX
        y *= scaleY
    }
}
