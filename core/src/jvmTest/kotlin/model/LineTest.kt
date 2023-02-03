package model

import org.junit.Assert.assertEquals
import org.junit.Test

internal class LineTest {

    @Test
    fun testHorizontalLine() {
        val startPoint = Point(0, 0)
        val endPoint = Point(50, 0)
        val line = Line(startPoint, endPoint)

        val expectedChunks = listOf(
            Chunk(0, 0),
            Chunk(1, 0),
            Chunk(2, 0),
            Chunk(3, 0),
            Chunk(4, 0),
            Chunk(5, 0)
        )
        assertEquals(expectedChunks, line.getListChunksForLine())
    }

    @Test
    fun testReverseHorizontalLine() {
        val startPoint = Point(50, 0)
        val endPoint = Point(0, 0)
        val line = Line(startPoint, endPoint)

        val expectedChunks = listOf(
            Chunk(5, 0),
            Chunk(4, 0),
            Chunk(3, 0),
            Chunk(2, 0),
            Chunk(1, 0),
            Chunk(0, 0)
        )
        assertEquals(expectedChunks, line.getListChunksForLine())
    }

    @Test
    fun testVerticalLine() {
        val startPoint = Point(0, 0)
        val endPoint = Point(0, 50)
        val line = Line(startPoint, endPoint)

        val expectedChunks = listOf(
            Chunk(0, 0),
            Chunk(0, 1),
            Chunk(0, 2),
            Chunk(0, 3),
            Chunk(0, 4),
            Chunk(0, 5)
        )
        assertEquals(expectedChunks, line.getListChunksForLine())
    }

    @Test
    fun testReverseVerticalLine() {
        val startPoint = Point(0, 50)
        val endPoint = Point(0, 0)
        val line = Line(startPoint, endPoint)

        val expectedChunks = listOf(
            Chunk(0, 5),
            Chunk(0, 4),
            Chunk(0, 3),
            Chunk(0, 2),
            Chunk(0, 1),
            Chunk(0, 0)
        )
        assertEquals(expectedChunks, line.getListChunksForLine())
    }

    @Test
    fun testDiagonalLine() {
        val startPoint = Point(0, 0)
        val endPoint = Point(50, 50)
        val line = Line(startPoint, endPoint)

        val expectedChunks = listOf(
            Chunk(0, 0),
            Chunk(1, 1),
            Chunk(2, 2),
            Chunk(3, 3),
            Chunk(4, 4),
            Chunk(5, 5)
        )
        assertEquals(expectedChunks, line.getListChunksForLine())
    }

    @Test
    fun testReverseDiagonalLine() {
        val startPoint = Point(50, 0)
        val endPoint = Point(0, 50)
        val line = Line(startPoint, endPoint)

        val expectedChunks = listOf(
            Chunk(5, 0),
            Chunk(4, 1),
            Chunk(3, 2),
            Chunk(2, 3),
            Chunk(1, 4),
            Chunk(0, 5)
        )
        assertEquals(expectedChunks, line.getListChunksForLine())
    }
}
