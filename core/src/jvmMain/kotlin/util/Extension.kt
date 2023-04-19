package util

import androidx.compose.ui.geometry.Offset
import model.Point

fun Offset.scale(scaleX: Number = 1, scaleY: Number = 1) = Offset(this.x * scaleX, this.y * scaleY)

fun Offset.move(offsetX: Number = 0, offsetY: Number = 0) = Offset(this.x + offsetX, this.y + offsetY)

fun Offset.toPoint() = Point(this.x, this.y)
