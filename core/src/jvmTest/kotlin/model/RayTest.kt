package model

import androidx.compose.ui.geometry.Offset
import org.junit.Assert.assertEquals
import org.junit.Test
import util.scale

internal class RayTest {
    private val xCoordinates = (0..10).random()
    private val yCoordinates = (0..10).random()

    private fun getRay() = Ray(Offset(0f, 0f), Offset(10f, 10f))

    private fun getRandomOffset() = Offset(xCoordinates.toFloat(), yCoordinates.toFloat())

    @Test
    fun `GIVEN ray WHEN the moveStart method is called THEN the start point moved`() {
        // GIVEN
        val ray = getRay()
        val startOffset = getRandomOffset()

        // WHEN
        ray.moveStart(startOffset)

        // THEN
        assertEquals(getRay().start + startOffset, ray.start)
        assertEquals(getRay().end, ray.end)
    }

    @Test
    fun `GIVEN ray WHEN the moveEnd method is called THEN the end point moved`() {
        // GIVEN
        val ray = getRay()
        val endOffset = getRandomOffset()

        // WHEN
        ray.moveEnd(endOffset)

        // THEN
        assertEquals(getRay().start, ray.start)
        assertEquals(getRay().end + endOffset, ray.end)
    }

    @Test
    fun `GIVEN ray WHEN the scaleStart method is called THEN the start point multiplied`() {
        // GIVEN
        val ray = getRay()
        val scaleX = (0..10).random()
        val scaleY = (0..10).random()

        // WHEN
        ray.scaleStart(scaleX = scaleX, scaleY = scaleY)

        // THEN
        assertEquals(getRay().start.scale(scaleX, scaleY), ray.start)
        assertEquals(getRay().end, ray.end)
    }

    @Test
    fun `GIVEN ray WHEN the scaleEnd method is called THEN the end point multiplied`() {
        // GIVEN
        val ray = getRay()
        val scaleX = (0..10).random()
        val scaleY = (0..10).random()

        // WHEN
        ray.scaleEnd(scaleX = scaleX, scaleY = scaleY)

        // THEN
        assertEquals(getRay().start, ray.start)
        assertEquals(getRay().end.scale(scaleX, scaleY), ray.end)
    }
}
