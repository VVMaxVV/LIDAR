package model

import kotlin.math.abs
import util.minus
import util.plus
import util.rem
import util.times

data class TiltAngle(private var angleOnXPlane: Number = 0) {
    var getAngleOnXPlane: Number = angleOnXPlane
        private set

    init {
        getAngleOnXPlane %= 360
    }

    fun rotateOnXPlane(angle: Number) {
        getAngleOnXPlane = if (getAngleOnXPlane + angle < 0) {
            360 - (abs(getAngleOnXPlane - angle * -1) % 360)
        } else {
            (getAngleOnXPlane + angle) % 360
        }
    }
}
