package mapper

import androidx.compose.ui.geometry.Offset
import model.Point

internal class OffsetMapper {
    fun toPoint(offset: Offset): Point {
        return Point(offset.x, offset.y)
    }
}
