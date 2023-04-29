package model

import util.minus

class Position(val currentCoordinates: Point, val currentTiltAngle: TiltAngle) {
    fun rotateOnXPlane(angle: Float): Position {
        return Position(currentCoordinates, currentTiltAngle.rotateOnXPlane(angle))
    }

    fun setAngle(angle: Float): Position {
        return Position(currentCoordinates, currentTiltAngle.rotateOnXPlane(angle - currentTiltAngle.getAngleOnXPlane))
    }

    fun setCoordinate(point: Point): Position {
        currentCoordinates.apply {
            x = point.x
            y = point.y
        }
        return Position(currentCoordinates, currentTiltAngle)
    }
}
