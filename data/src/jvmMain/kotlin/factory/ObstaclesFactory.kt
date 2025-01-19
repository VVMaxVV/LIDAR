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
                        Point(-90, -90),
                        Point(-90, 90),
                    ),
                    Line(
                        Point(-90, 90),
                        Point(90, 90),
                    ),
                    Line(
                        Point(90, 90),
                        Point(90, -90),
                    ),
                    Line(
                        Point(90, -90),
                        Point(-90, -90),
                    ),
                )
            ),
            Obstacle(
                listOf(
                    Line(
                        Point(-90, 60),
                        Point(-70, 60)
                    ),
                    Line(
                        Point(-60, 60),
                        Point(-60, 90)
                    ),
                    Line(
                        Point(-50, 55),
                        Point(-50, 90)
                    ),
                    Line(
                        Point(-50, 65),
                        Point(-30, 65),
                    ),
                    Line(
                        Point(-20, 55),
                        Point(-20, 90)
                    ),
                    Line(
                        Point(-20, 55),
                        Point(0, 55)
                    ),
                    Line(
                        Point(0, 55),
                        Point(0, 90)
                    ),
                    Line(
                        Point(10, 65),
                        Point(10, -10)
                    ),
                    Line(
                        Point(10, 60),
                        Point(25, 60)
                    ),
                    Line(
                        Point(35, 60),
                        Point(50, 60)
                    ),
                    Line(
                        Point(35, 70),
                        Point(50, 70)
                    ),
                    Line(
                        Point(35, 60),
                        Point(35, 70)
                    ),
                    Line(
                        Point(50, 60),
                        Point(50, 70)
                    ),
                    Line(
                        Point(60, 55),
                        Point(60, 75)
                    ),
                    Line(
                        Point(70, 60),
                        Point(70, 80)
                    ),
                    Line(
                        Point(80, 60),
                        Point(80, 80)
                    ),
                    Line(
                        Point(70, 60),
                        Point(80, 60)
                    ),
                    Line(
                        Point(70, 80),
                        Point(80, 80)
                    ),
                    Line(
                        Point(70, 50),
                        Point(70, 20)
                    ),
                    Line(
                        Point(70, 50),
                        Point(90, 50)
                    ),
                    Line(
                        Point(70, 20),
                        Point(90, 20)
                    ),
                    Line(
                        Point(50, 50),
                        Point(50, 0)
                    ),
                    Line(
                        Point(0, 40),
                        Point(30, 40)
                    ),
                    Line(
                        Point(25, 0),
                        Point(40, 0)
                    ),
                    Line(
                        Point(25, 15),
                        Point(40, 15)
                    ),
                    Line(
                        Point(25, 0),
                        Point(25, 15)
                    ),
                    Line(
                        Point(40, 0),
                        Point(40, 15)
                    ),
                    Line(
                        Point(35, 0),
                        Point(35, -30)
                    ),
                    Line(
                        Point(50, -40),
                        Point(50, -90)
                    ),
                    Line(
                        Point(60, -40),
                        Point(90, -40)
                    ),
                    Line(
                        Point(50, 10),
                        Point(75, 10)
                    ),
                    Line(
                        Point(-80, -60),
                        Point(-60, -60)
                    ),
                    Line(
                        Point(-60, -60),
                        Point(-60, -80)
                    ),
                    Line(
                        Point(-75, -40),
                        Point(-50, -40)
                    ),
                    Line(
                        Point(-75, -20),
                        Point(-50, -20)
                    ),
                    Line(
                        Point(-50, -40),
                        Point(-50, -20)
                    ),
                    Line(
                        Point(-75, -40),
                        Point(-75, -20)
                    ),
                    Line(
                        Point(-90, 10),
                        Point(-5, 10)
                    ),
                    Line(
                        Point(-75, 25),
                        Point(-50, 25)
                    ),
                    Line(
                        Point(-75, 40),
                        Point(-50, 40)
                    ),
                    Line(
                        Point(-75, 25),
                        Point(-75, 40)
                    ),
                    Line(
                        Point(-50, 25),
                        Point(-50, 40)
                    ),
                    Line(
                        Point(-30,-10),
                        Point(20,-10)
                    ),
                    Line(
                        Point(-30,-50),
                        Point(20,-50)
                    ),
                    Line(
                        Point(-30,-10),
                        Point(-30,-25)
                    ),
                    Line(
                        Point(-30,-35),
                        Point(-30,-50)
                    ),
                    Line(
                        Point(20,-10),
                        Point(20,-15)
                    ),
                    Line(
                        Point(20,-30),
                        Point(20,-50)
                    ),
                    Line(
                        Point(-40,-75),
                        Point(20,-75)
                    ),
                    Line(
                        Point(-40,-75),
                        Point(-40,-90)
                    ),
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
