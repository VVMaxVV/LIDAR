package factory

import exception.LocationNotInstalledException
import model.Chunk
import model.ChunkHashMap
import model.Line
import model.Obstacle
import model.Point
import model.sealedClass.LocationSample

internal class ObstaclesFactory {
    private val listMapObstacle = mutableListOf(ChunkHashMap(), ChunkHashMap(), ChunkHashMap())
    private val listLocation: MutableList<MutableList<Obstacle>> = mutableListOf(
        mutableListOf(
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
                    )
                )
            )
        ),
        mutableListOf(
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
        ),
        mutableListOf(
            Obstacle(
                listOf(
                    Line(
                        Point(-10, -10),
                        Point(-10, 10)
                    ),
                    Line(
                        Point(-10, 15),
                        Point(-10, 20)
                    ),
                    Line(
                        Point(-20, 20),
                        Point(10, 20)
                    ),
                    Line(
                        Point(10, 20),
                        Point(10, -10)
                    ),
                    Line(
                        Point(-10, 7),
                        Point(6, 7)
                    ),
                    Line(
                        Point(-20, -10),
                        Point(10, -10)
                    ),
                    Line(
                        Point(-20, -10),
                        Point(-20, 20)
                    )
                )
            )
        )
    )

    private var currentLocation: LocationSample? = null

    init {
        listLocation.mapIndexed { index, listObstacles ->
            listObstacles.map { obstacle ->
                obstacle.listOfLines.map { line ->
                    line.getListChunksForLine().map { chunk ->
                        listMapObstacle[index][chunk]?.add(line)
                    }
                }
            }
        }
        changeLocation(LocationSample.FirstSample)
    }

    fun getAll(): List<Obstacle> = listLocation[0]

    fun get(topLeftPoint: Point, bottomRightPoint: Point): List<Line> {
        currentLocation?.let { location ->
            val listLines = mutableSetOf<Line>()
            for (
            y in minOf(topLeftPoint.getChunk().y, bottomRightPoint.getChunk().y)..maxOf(
                topLeftPoint.getChunk().y,
                bottomRightPoint.getChunk().y
            )
            ) {
                for (
                x in minOf(topLeftPoint.getChunk().x, bottomRightPoint.getChunk().x)..maxOf(
                    topLeftPoint.getChunk().x,
                    bottomRightPoint.getChunk().x
                )
                ) {
                    listMapObstacle[location.getIndex()][Chunk(x, y)]?.let {
                        listLines.addAll(it)
                    }
                }
            }
            return listLines.toList()
        }
        throw LocationNotInstalledException()
    }

    fun changeLocation(sample: LocationSample) {
        currentLocation = sample
    }
}
