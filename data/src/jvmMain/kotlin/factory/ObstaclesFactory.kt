package factory

import model.Chunk
import model.ChunkHashMap
import model.Line
import model.Obstacle
import model.Point

internal class ObstaclesFactory {
    private val listObstacles: MutableList<Obstacle> = mutableListOf()
    private val mapObstacle = ChunkHashMap()

    init {
        listObstacles.apply {
            clear()
            add(
                Obstacle(
                    listOf(
                        Line(
                            Point(-10, -10),
                            Point(-10, 20)
                        ),
                        Line(
                            Point(-10, 20),
                            Point(10, 20)
                        ),
                        Line(
                            Point(10, 20),
                            Point(10, -10)
                        ),
                        Line(
                            Point(10, -10),
                            Point(-10, -10)
                        ),
                        Line(
                            Point(-10, 7),
                            Point(6, 7)
                        )
                    )
                )
            )
        }
        listObstacles.map { obstacle ->
            obstacle.listOfLines.map { line ->
                line.getListChunksForLine().map { chunk ->
                    mapObstacle[chunk]?.add(line)
                }
            }
        }
        listObstacles
    }

    fun getAll(): List<Obstacle> = listObstacles

    fun get(topLeftPoint: Point, bottomRightPoint: Point): List<Line> {
        val listLines = mutableSetOf<Line>()
        for (
        y in minOf(topLeftPoint.getChunk().y, bottomRightPoint.getChunk().y)..maxOf(topLeftPoint.getChunk().y, bottomRightPoint.getChunk().y)
        ) {
            for (
            x in minOf(topLeftPoint.getChunk().x, bottomRightPoint.getChunk().x)..maxOf(topLeftPoint.getChunk().x, bottomRightPoint.getChunk().x)
            ) {
                mapObstacle[Chunk(x, y)]?.let { listLines.addAll(it) }
            }
        }
        return listLines.toList()
    }
}
