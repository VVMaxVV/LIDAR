package util

import model.Point
import model.Ray
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun Number.toRadians() = this.toFloat() * PI / 180

operator fun Number.plus(i: Number) = this.toFloat() + i.toFloat()

operator fun Number.minus(i: Number) = this.toFloat() - i.toFloat()

operator fun Number.div(i: Number) = this.toFloat() / i.toFloat()

operator fun Number.times(i: Number) = this.toFloat() * i.toFloat()

operator fun Number.rem(i: Number) = this.toFloat() % i.toFloat()

operator fun Number.compareTo(number: Number): Int = (this - number).toInt()

fun getX(distance: Number, angle: Number, currentPositionX: Number = 0.0) =
    cos(angle.toDouble().toRadians()) * distance + currentPositionX

fun getY(distance: Number, angle: Number, currentPositionY: Number = 0.0) =
    sin(angle.toDouble().toRadians()) * distance + currentPositionY

fun getDistanceToIntersection(firstLine: Ray, secondLine: Ray): Number? =
    getPointIntersectionOfLines(firstLine, secondLine)?.getDistance(firstLine.start)

fun getPointIntersectionOfLines(firstLine: Ray, secondLine: Ray): Point? {
    val denominator =
        (firstLine.start.x - firstLine.end.x) * (secondLine.start.y - secondLine.end.y) -
            (firstLine.start.y - firstLine.end.y) * (secondLine.start.x - secondLine.end.x)
    if (denominator == 0f) {
        return null
    }

    val t =
        (
            (firstLine.start.x - secondLine.start.x) * (secondLine.start.y - secondLine.end.y) -
                (firstLine.start.y - secondLine.start.y) *
                (secondLine.start.x - secondLine.end.x)
            ) / denominator
    val u =
        -(
            (firstLine.start.x - firstLine.end.x) * (firstLine.start.y - secondLine.start.y) -
                (firstLine.start.y - firstLine.end.y) *
                (firstLine.start.x - secondLine.start.x)
            ) / denominator

    if (t in 0f..1f && u in 0f..1f) {
        val x = firstLine.start.x + t * (firstLine.end.x - firstLine.start.x)
        val y = firstLine.start.y + t * (firstLine.end.y - firstLine.start.y)
        return Point(x, y)
    }

    return null
}
