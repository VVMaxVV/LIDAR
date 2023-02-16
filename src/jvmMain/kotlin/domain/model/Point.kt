package domain.model

import androidx.compose.ui.geometry.Offset
import domain.utils.minus
import domain.utils.plus
import domain.utils.times
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class Point(var x: Number = 0, var y: Number = 0) {
    fun move(offset: Offset) {
        x += offset.x
        y += offset.y
    }

    fun scale(scaleX: Number = 1, scaleY: Number = 1) {
        x *= scaleX
        y *= scaleY
    }

    fun getDistance(point: Offset) = sqrt(abs(point.x - this.x).pow(2) + abs(point.y - this.y).pow(2))
}
