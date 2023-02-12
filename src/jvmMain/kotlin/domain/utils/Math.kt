package domain.utils

import kotlin.math.PI

fun Number.toRadians() = this.toFloat() * PI / 180

operator fun Number.plus(i: Number) = this.toFloat() + i.toFloat()

operator fun Number.minus(i: Number) = this.toFloat() - i.toFloat()

operator fun Number.div(i: Number) = this.toFloat() / i.toFloat()

operator fun Number.times(i: Number) = this.toFloat() * i.toFloat()
