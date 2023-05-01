package model

import util.rem

class TiltAngle(private var angleOnXPlane: Number = 0) {
    var getAngleOnXPlane: Number = angleOnXPlane
        private set

    init {
        getAngleOnXPlane %= 360
    }

    fun rotateOnXPlane(angle: Number): TiltAngle {
        var newAngle = (angleOnXPlane.toDouble() + angle.toDouble()) % 360
        if (newAngle < 0) {
            newAngle += 360
        }
        getAngleOnXPlane = newAngle
        return TiltAngle(getAngleOnXPlane)
    }
}
