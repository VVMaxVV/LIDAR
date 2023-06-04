package util

import androidx.compose.ui.geometry.Offset

fun Offset.scale(scaleX: Number = 1, scaleY: Number = 1) = Offset(this.x * scaleX, this.y * scaleY)

fun Offset.move(offsetX: Number = 0, offsetY: Number = 0) = Offset(this.x + offsetX, this.y + offsetY)

fun isCoordinate(value: String): Boolean = value.isBlank() || value == "-"
