package model

import util.CHUNK_SIZE
import util.compareTo
import util.minus
import util.plus
import kotlin.math.abs

data class Line(val startPoint: Point, val endPoint: Point) {
    fun getListChunksForLine(): List<Chunk> {
        val listChunks = mutableListOf<Chunk>()
        val dx = this.endPoint.x - this.startPoint.x
        val dy = this.endPoint.y - this.startPoint.y
        val sx = if (this.startPoint.x < this.endPoint.x) 1 else -1
        val sy = if (this.startPoint.y < this.endPoint.y) 1 else -1

        when {
            this.startPoint.getChunk().y == this.endPoint.getChunk().y -> {
                for (
                i in minOf(this.startPoint.getChunk().x, this.endPoint.getChunk().x)..maxOf(this.startPoint.getChunk().x, this.endPoint.getChunk().x)
                ) {
                    listChunks.add(Chunk(i, this.startPoint.getChunk().y))
                }
            }

            this.startPoint.getChunk().x == this.endPoint.getChunk().x -> {
                for (
                i in minOf(this.startPoint.getChunk().y, this.endPoint.getChunk().y)..maxOf(this.startPoint.getChunk().y, this.endPoint.getChunk().y)
                ) {
                    listChunks.add(Chunk(this.startPoint.getChunk().x, i))
                }
            }

            else -> {
                val slope = dx / dy
                val currentPoint = this.startPoint
                listChunks.add(currentPoint.getChunk())
                while (listChunks.last() != this.endPoint.getChunk()) {
                    val chunkBorderX = (currentPoint.getChunk().x * CHUNK_SIZE + (CHUNK_SIZE / 2 * sx))
                    val chunkBorderY = (currentPoint.getChunk().y * CHUNK_SIZE + (CHUNK_SIZE / 2 * sy))
                    val chunkBorderDeltaX = abs(chunkBorderX - currentPoint.x) * sx
                    val chunkBorderDeltaY = abs(chunkBorderY - currentPoint.y) * sy
                    val incrementX = chunkBorderDeltaX / slope
                    when {
                        (incrementX < chunkBorderDeltaY) -> {
                            listChunks.add(Chunk(currentPoint.getChunk().x + sx, currentPoint.getChunk().y))
                            currentPoint.apply {
                                x = chunkBorderX
                                y += chunkBorderDeltaX / slope
                            }
                        }

                        (incrementX > chunkBorderDeltaY) -> {
                            listChunks.add(Chunk(currentPoint.getChunk().x, currentPoint.getChunk().y + sy))
                            currentPoint.apply {
                                x += chunkBorderDeltaY / slope
                                y = chunkBorderY
                            }
                        }

                        (incrementX == chunkBorderDeltaY) -> {
                            currentPoint.apply {
                                x = chunkBorderX + CHUNK_SIZE / 2 * sx
                                y = chunkBorderY + CHUNK_SIZE / 2 * sy
                            }
                            listChunks.add(currentPoint.getChunk())
                        }
                    }
                }
            }
        }
        return listChunks
    }
}
