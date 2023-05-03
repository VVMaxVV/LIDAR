package model

import androidx.compose.ui.geometry.Offset
import util.CHUNK_SIZE
import util.compareTo
import util.minus
import util.plus
import util.times
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class Point(var x: Number = 0, var y: Number = 0) {
    fun move(offset: Offset) {
        x += offset.x
        y += offset.y
    }

    fun moveTo(offset: Offset) {
        x = offset.x
        y = offset.y
    }

    fun scale(scaleX: Number = 1, scaleY: Number = 1) {
        x *= scaleX
        y *= scaleY
    }

    fun getDistance(offset: Offset) = sqrt(abs(offset.x - this.x).pow(2) + abs(offset.y - this.y).pow(2))

    fun getDistance(point: Point) = sqrt(abs(point.x - this.x).pow(2) + abs(point.y - this.y).pow(2))

    fun getChunk(): Chunk {
        val xChunkIndex =
            if (this.x >= 0) ((this.x + CHUNK_SIZE / 2) / CHUNK_SIZE).toInt() else ((this.x - CHUNK_SIZE / 2) / CHUNK_SIZE).toInt()
        val yChunkIndex =
            if (this.y >= 0) ((this.y + CHUNK_SIZE / 2) / CHUNK_SIZE).toInt() else ((this.y - CHUNK_SIZE / 2) / CHUNK_SIZE).toInt()
        return Chunk(xChunkIndex, yChunkIndex)
    }
}
