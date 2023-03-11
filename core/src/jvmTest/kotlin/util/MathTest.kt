package util

import androidx.compose.ui.geometry.Offset
import model.Point
import model.Ray
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import kotlin.math.PI
import kotlin.math.sqrt

private const val ACCURACY = 0.001

internal class MathTest {
    @Test
    fun `GIVEN angle in degree WHEN the toRadians method is called THEN expected angle in radians`() {
        assertEquals(0.0, 0.toRadians(), ACCURACY)
        assertEquals(PI / 4, 45.toRadians(), ACCURACY)
        assertEquals(PI / 2, 90.toRadians(), ACCURACY)
        assertEquals(PI, 180.toRadians(), ACCURACY)
        assertEquals(1.5 * PI, 270.toRadians(), ACCURACY)
        assertEquals(2 * PI, 360.toRadians(), ACCURACY)
        assertEquals(3 * PI, 540.toRadians(), ACCURACY)
        assertEquals(4 * PI, 720.toRadians(), ACCURACY)
    }

    @Test
    fun `GIVEN number WHEN the plus operator is called THEN sum of 2 numbers`() {
        assertEquals(2f, 1.plus(1 as Number))
        assertEquals(4f, 2f.plus(2 as Number))
        assertEquals(-1.5f, (-2.5).plus(1 as Number))
        assertEquals(10.2f, 3.2.plus(7 as Number))
        assertEquals(0f, 0.plus(0 as Number))
    }

    @Test
    fun `GIVEN number WHEN the minus operator is called THEN minus of 2 numbers`() {
        assertEquals(0f, 1.minus(1 as Number))
        assertEquals(0f, 2f.minus(2 as Number))
        assertEquals(-1.5f, (-0.5).minus(1.0 as Number))
        assertEquals(5.5f, 10.7.minus(5.2 as Number))
        assertEquals(0f, 0.minus(0 as Number))
    }

    @Test
    fun `GIVEN number WHEN the division operator is called THEN dividing the first number by the second`() {
        assertEquals(1f, 2.div(2 as Number))
        assertEquals(-0.5f, (-1.0).div(2 as Number))
        assertEquals(2f, 10.div(5 as Number))
        assertEquals(Float.POSITIVE_INFINITY, 5.div(0 as Number))
    }

    @Test
    fun `GIVEN number WHEN the times operator is called THEN multiply two numbers`() {
        assertEquals(4f, 2.times(2 as Number))
        assertEquals(-2.5f, (-0.5).times(5 as Number))
        assertEquals(35.7f, 5.1.times(7 as Number))
        assertEquals(0f, 0.times(5 as Number))
    }

    @Test
    fun `GIVEN number WHEN the rem operator is called THEN dividing the first number by the second`() {
        assertEquals(0f, 2.rem(2 as Number))
        assertEquals(-0.5f, (-0.5).rem(1 as Number))
        assertEquals(0.7f, 10.7.rem(5 as Number), ACCURACY.toFloat())
        assertEquals(Float.NaN, 5.rem(0 as Number))
    }

    @Test
    fun `GIVEN number WHEN the compareTo operator is called THEN expected -1 if second is greater then first, 0 if they equal, 1 if first is greater then second`() {
        // GIVEN
        val first = 10 as Number
        val second = 5 as Number
        val third = 10 as Number

        // WHEN
        val firstResult = first.compareTo(second) // 1 (10 > 5)
        val secondResult = second.compareTo(first) // -1 (5 < 10)
        val thirdResult = first.compareTo(third) // 0 (10 == 10)

        // THEN
        assertEquals(1, firstResult)
        assertEquals(-1, secondResult)
        assertEquals(0, thirdResult)
    }

    @Test
    fun `GIVEN distance, angle, current position WHEN the getX method is called THEN expected x coordinate satisfying the conditions of the arguments`() {
        // GIVEN
        val distance = sqrt(2f) as Number
        val angle = 45 as Number
        val currentPositionX = 1 as Number
        val expectedX = 2f

        // WHEN
        val actualX = getX(distance, angle, currentPositionX)

        // THEN
        assertEquals(expectedX, actualX, ACCURACY.toFloat())
    }

    @Test
    fun `GIVEN distance, angle and currentPositionX are zero WHEN the getX method called THEN expected x coordinate satisfying the conditions of the arguments`() {
        val distance = 0
        val angle = 0
        val currentPositionX = 0
        val expectedX = 0f

        val actualX = getX(distance, angle, currentPositionX)

        assertEquals(expectedX, actualX, ACCURACY.toFloat())
    }

    @Test
    fun `GIVEN distance and angle are zero WHEN the getX method called THEN expected x coordinate satisfying the conditions of the arguments`() {
        val distance = 0
        val angle = 0
        val currentPositionX = 1
        val expectedX = 1f

        val actualX = getX(distance, angle, currentPositionX)

        assertEquals(expectedX, actualX, ACCURACY.toFloat())
    }

    @Test
    fun `GIVEN currentPositionX is zero WHEN the getX method called THEN expected x coordinate satisfying the conditions of the arguments`() {
        val distance = sqrt(2f)
        val angle = 45
        val currentPositionX = 0
        val expectedX = 1f

        val actualX = getX(distance, angle, currentPositionX)

        assertEquals(expectedX, actualX, ACCURACY.toFloat())
    }

    @Test
    fun `GIVEN currentPositionX is not zero WHEN the getX method called THEN expected x coordinate satisfying the conditions of the arguments`() {
        val distance = sqrt(2f)
        val angle = 45
        val currentPositionX = 1
        val expectedX = 2f

        val actualX = getX(distance, angle, currentPositionX)

        assertEquals(expectedX, actualX, ACCURACY.toFloat())
    }

    @Test
    fun `GIVEN distance, angle, current position WHEN the getY method is called THEN expected y coordinate satisfying the conditions of the arguments`() {
        // GIVEN
        val distance = sqrt(2f)
        val angle = 45
        val currentPositionY = 1
        val expectedY = 2f

        // WHEN
        val actualY = getY(distance, angle, currentPositionY)

        // THEN
        assertEquals(expectedY, actualY, ACCURACY.toFloat())
    }

    @Test
    fun `GIVEN distance, angle and currentPositionY are zero WHEN the getY method called THEN expected y coordinate satisfying the conditions of the arguments`() {
        val distance = 0
        val angle = 0
        val currentPositionY = 0
        val expectedY = 0f

        val actualY = getY(distance, angle, currentPositionY)

        assertEquals(expectedY, actualY, ACCURACY.toFloat())
    }

    @Test
    fun `GIVEN distance and angle are zero WHEN the getY method called THEN expected y coordinate satisfying the conditions of the arguments`() {
        val distance = 0
        val angle = 0
        val currentPositionY = 1
        val expectedY = 1f

        val actualY = getY(distance, angle, currentPositionY)

        assertEquals(expectedY, actualY, ACCURACY.toFloat())
    }

    @Test
    fun `GIVEN currentPositionY is zero WHEN the getY method called THEN expected y coordinate satisfying the conditions of the arguments`() {
        val distance = sqrt(2f)
        val angle = 45
        val currentPositionY = 0
        val expectedY = 1f

        val actualY = getY(distance, angle, currentPositionY)

        assertEquals(expectedY, actualY, ACCURACY.toFloat())
    }

    @Test
    fun `GIVEN currentPositionY is not zero WHEN the getY method called THEN expected y coordinate satisfying the conditions of the arguments`() {
        val distance = sqrt(2f)
        val angle = 45
        val currentPositionY = 1
        val expectedY = 2f

        val actualY = getY(distance, angle, currentPositionY)

        assertEquals(expectedY, actualY, ACCURACY.toFloat())
    }

    @Test
    fun `GIVEN two intersection rays WHEN the getDistanceToIntersection method is called THEN expected distance from the starting point of the first beam to the intersection with the second`() {
        // GIVEN
        val firstLine = Ray(Offset(0f, 0f), Offset(0f, 10f))
        val secondLine = Ray(Offset(-10f, 5f), Offset(10f, 5f))
        val expectedDistance = 5.0

        // WHEN
        val actualDistance = getDistanceToIntersection(firstLine, secondLine)

        // THEN
        if (actualDistance != null) {
            assertEquals(expectedDistance, actualDistance.toDouble(), ACCURACY)
        } else {
            assert(false)
        }
    }

    @Test
    fun `GIVEN two rays WHEN the getDistanceToIntersection method is called THEN expected distance from the starting point of the first beam to the intersection with the second`() {
        // GIVEN
        val firstLine = Ray(Offset(0f, 0f), Offset(0f, 4f))
        val secondLine = Ray(Offset(-10f, 5f), Offset(10f, 5f))

        // WHEN
        val actualDistance = getDistanceToIntersection(firstLine, secondLine)

        // THEN
        assertNull(actualDistance)
    }

    @Test
    fun `GIVEN two parallel rays WHEN the getPointIntersectionOfLines is called THEN expected null`() {
        // GIVEN
        val firstRay = Ray(Offset(0f, 0f), Offset(0f, 1f))
        val secondRay = Ray(Offset(1f, 0f), Offset(1f, 1f))

        // WHEN
        val result = getPointIntersectionOfLines(firstRay, secondRay)

        // THEN
        assertNull(result)
    }

    @Test
    fun `GIVEN two intersection rays WHEN the getPointIntersectionOfLines is called THEN expected intersection point`() {
        // GIVEN
        val firstRay = Ray(Offset(0f, 0f), Offset(0f, 10f))
        val secondRay = Ray(Offset(-1f, 5f), Offset(1f, 5f))
        val expected = Point(0f, 5f)

        // WHEN
        val result = getPointIntersectionOfLines(firstRay, secondRay)

        // THEN
        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN two non-intersecting rays && first above second WHEN the getPointIntersectionOfLines is called THEN expected null`() {
        // GIVEN
        val firstRay = Ray(Offset(0f, 0f), Offset(1f, 1f))
        val secondRay = Ray(Offset(0f, 1f), Offset(1f, 2f))

        // WHEN
        val result = getPointIntersectionOfLines(firstRay, secondRay)

        // THEN
        assertNull(result)
    }

    @Test
    fun `GIVEN two non-intersecting rays && first below second WHEN the getPointIntersectionOfLines is called THEN expected null`() {
        // GIVEN
        val firstRay = Ray(Offset(0f, 0f), Offset(1f, 1f))
        val secondRay = Ray(Offset(1f, 0f), Offset(2f, 1f))

        // WHEN
        val result = getPointIntersectionOfLines(firstRay, secondRay)

        // THEN
        assertNull(result)
    }

    @Test
    fun `GIVEN two non-intersecting rays && first left of second WHEN the getPointIntersectionOfLines is called THEN expected null`() {
        // GIVEN
        val firstRay = Ray(Offset(0f, 0f), Offset(1f, 1f))
        val secondRay = Ray(Offset(1f, 0f), Offset(2f, 1f))

        // WHEN
        val result = getPointIntersectionOfLines(firstRay, secondRay)

        // THEN
        assertNull(result)
    }

    @Test
    fun `GIVEN two non-intersecting rays && first right of second WHEN the getPointIntersectionOfLines is called THEN expected null`() {
        // GIVEN
        val firstRay = Ray(Offset(1f, 0f), Offset(2f, 1f))
        val secondRay = Ray(Offset(0f, 0f), Offset(1f, 1f))

        // WHEN
        val result = getPointIntersectionOfLines(firstRay, secondRay)

        // THEN
        assertNull(result)
    }

    @Test
    fun `GIVEN two non-intersecting rays && test intersection before start first ray WHEN the getPointIntersectionOfLines is called THEN expected null`() {
        // GIVEN
        val firstRay = Ray(Offset(0f, 0f), Offset(1f, 1f))
        val secondRay = Ray(Offset(2f, 0f), Offset(2f, 2f))

        // WHEN
        val result = getPointIntersectionOfLines(firstRay, secondRay)

        // THEN
        assertNull(result)
    }

    @Test
    fun `GIVEN two non-intersecting rays && test intersection after end first ray WHEN the getPointIntersectionOfLines is called THEN expected null`() {
        // GIVEN
        val firstRay = Ray(Offset(0f, 0f), Offset(1f, 1f))
        val secondRay = Ray(Offset(-1f, 1f), Offset(-0.5f, 1.5f))

        // WHEN
        val result = getPointIntersectionOfLines(firstRay, secondRay)

        // THEN
        assertNull(result)
    }

    @Test
    fun `GIVEN two non-intersecting rays && test intersection before start second ray WHEN the getPointIntersectionOfLines is called THEN expected null`() {
        // GIVEN
        val firstRay = Ray(Offset(0f, 0f), Offset(1f, 1f))
        val secondRay = Ray(Offset(1f, 2f), Offset(2f, 2f))

        // WHEN
        val result = getPointIntersectionOfLines(firstRay, secondRay)

        // THEN
        assertNull(result)
    }

    @Test
    fun `GIVEN two non-intersecting rays && test intersection after end second ray WHEN the getPointIntersectionOfLines is called THEN expected null`() {
        // GIVEN
        val firstRay = Ray(Offset(0f, 0f), Offset(1f, 1f))
        val secondRay = Ray(Offset(2f, 2f), Offset(3f, 3f))

        // WHEN
        val result = getPointIntersectionOfLines(firstRay, secondRay)

        // THEN
        assertNull(result)
    }
}
