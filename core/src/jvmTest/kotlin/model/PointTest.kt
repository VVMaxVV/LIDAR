package model

import androidx.compose.ui.geometry.Offset
import org.junit.Assert.assertEquals
import org.junit.Test
import util.plus
import util.times
import kotlin.math.sqrt

internal class PointTest {
    private val pointXCoordinates = (0..10).random()
    private val pointYCoordinates = (0..10).random()
    private val offsetXValue = (0..10).random().toFloat()
    private val offsetYValue = (0..10).random().toFloat()
    private val scaleX = (0..10).random()
    private val scaleY = (0..10).random()

    private fun getPoint() = Point(pointXCoordinates, pointYCoordinates)

    private fun getOffset() = Offset(offsetXValue, offsetYValue)

    @Test
    fun `GIVEN point WHEN the move method is called THEN the point changes the value`() {
        // GIVEN
        val point = getPoint()
        val offset = getOffset()

        // WHEN
        point.move(offset)

        // THEN
        assertEquals(getPoint().x + getOffset().x, point.x)
        assertEquals(getPoint().y + getOffset().y, point.y)
    }

    @Test
    fun `GIVEN point WHEN the moveTo method is called THEN the point changes the value`() {
        // GIVEN
        val point = getPoint()
        val offset = getOffset()

        // WHEN
        point.moveTo(offset)

        // THEN
        assertEquals(getOffset().x, point.x)
        assertEquals(getOffset().y, point.y)
    }

    @Test
    fun `GIVEN point WHEN the scale method is called with two arg THEN the point coordinates multiplied by the scale values`() {
        // GIVEN
        val point = getPoint()
        val scaleX = scaleX
        val scaleY = scaleY

        // WHEN
        point.scale(scaleX, scaleY)

        // THEN
        assertEquals(getPoint().x * scaleX, point.x)
        assertEquals(getPoint().y * scaleY, point.y)
    }

    @Test
    fun `GIVEN point WHEN the scale method is called with xScale arg THEN the point x coordinate multiplied by the scale value`() {
        // GIVEN
        val point = getPoint()
        val scaleX = scaleX

        // WHEN
        point.scale(scaleX = scaleX)

        // THEN
        assertEquals(getPoint().x * scaleX, point.x)
        assertEquals(getPoint().y.toFloat(), point.y)
    }

    @Test
    fun `GIVEN point WHEN the scale method is called with yScale arg THEN the point y coordinate multiplied by the scale value`() {
        // GIVEN
        val point = getPoint()
        val scaleY = scaleY

        // WHEN
        point.scale(scaleY = scaleY)

        // THEN
        assertEquals(getPoint().x.toFloat(), point.x)
        assertEquals(getPoint().y * scaleY, point.y)
    }

    @Test
    fun `GIVEN point WHEN the getDistance method is called with yScale arg THEN returned value between point and offset`() {
        // GIVEN
        val point = Point(0, 0)
        val offset = Offset(2f, 2f)

        // WHEN
        val distance = point.getDistance(offset)

        // THEN
        assertEquals(distance, 2 * sqrt(2f))
    }
}
