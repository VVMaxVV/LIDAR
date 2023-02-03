package util

import androidx.compose.ui.geometry.Offset
import org.junit.Assert.assertEquals
import org.junit.Test

internal class ExtensionTest {
    private val valueX = (0..10).random()
    private val valueY = (0..10).random()
    private val scaleX = (0..10).random()
    private val scaleY = (0..10).random()
    private val offsetX = (0..10).random()
    private val offsetY = (0..10).random()

    private fun getOffset() = Offset(valueX.toFloat(), valueY.toFloat())

    @Test
    fun `GIVEN offset object WHEN the scale method is called THEN offset value multiplied by the value`() {
        // GIVEN
        val offset = getOffset()

        // WHEN
        val resultOffset = offset.scale(scaleX, scaleY)

        // THEN
        assertEquals(getOffset().x * scaleX, resultOffset.x)
        assertEquals(getOffset().y * scaleY, resultOffset.y)
    }

    @Test
    fun `GIVEN offset object WHEN the move method is called THEN offset value plus offset values`() {
        // GIVEN
        val offset = getOffset()

        // WHEN
        val resultOffset = offset.move(offsetX, offsetY)

        // THEN
        assertEquals(getOffset().x + offsetX, resultOffset.x)
        assertEquals(getOffset().y + offsetY, resultOffset.y)
    }
}
