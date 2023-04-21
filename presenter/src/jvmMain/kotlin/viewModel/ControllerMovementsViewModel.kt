package viewModel

import androidx.compose.ui.geometry.Offset
import model.Line
import model.Point
import model.Position
import useCase.GetNearestObstaclesUseCase
import useCase.SetCurrentPositionUseCase
import util.consts.DefaultValues
import util.getX
import util.getY
import util.isLineIntersection
import util.plus

internal class ControllerMovementsViewModel(
    private val setCurrentPositionUseCase: SetCurrentPositionUseCase,
    private val getNearestObstaclesUseCase: GetNearestObstaclesUseCase
) {
    private var currentPosition: Position? = null
    fun move(movements: Movements) {
        currentPosition?.let {
            when (movements) {
                Movements.MoveLeft -> {
                    val newCoordinate = Point(
                        getX(-1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                        getY(-1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                    )
                    if (isMovingPossible(newCoordinate)) {
                        it.currentCoordinates.moveTo(
                            Offset(
                                getX(-1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                                getY(-1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                            )
                        )
                    }
                }

                Movements.MoveRight -> {
                    val newCoordinate = Point(
                        getX(1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                        getY(1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                    )
                    if (isMovingPossible(newCoordinate)) {
                        it.currentCoordinates.moveTo(
                            Offset(
                                getX(1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                                getY(1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                            )
                        )
                    }
                }

                Movements.MoveForward -> {
                    val newCoordinate = Point(
                        getX(1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                        getY(1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                    )
                    if (isMovingPossible(newCoordinate)) {
                        it.currentCoordinates.moveTo(
                            Offset(
                                getX(1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                                getY(1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                            )
                        )
                    }
                }

                Movements.MoveBackward -> {
                    val newCoordinate = Point(
                        getX(-1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                        getY(-1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                    )
                    if (isMovingPossible(newCoordinate)) {
                        it.currentCoordinates.moveTo(
                            Offset(
                                getX(-1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                                getY(-1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                            )
                        )
                    }
                }

                Movements.RotateClockwise -> it.currentTiltAngle.rotateOnXPlane(-5)
                Movements.RotateCounterclockwise -> it.currentTiltAngle.rotateOnXPlane(5)
            }
            setCurrentPositionUseCase.execute(it)
        }
    }

    fun setCurrentPosition(position: Position) {
        currentPosition = position
        setCurrentPositionUseCase.execute(position)
    }

    private fun isMovingPossible(newPosition: Point): Boolean {
        currentPosition?.currentCoordinates?.let { currentCoordinate ->
            getNearestObstaclesUseCase.execute(
                currentCoordinate,
                DefaultValues.SIZE_CHECKED_ZONE_AROUND_POSITION / 2f
            ).forEach {
                if (isLineIntersection(Line(currentCoordinate, newPosition), it)) return false
            }
        }
        return true
    }

    sealed class Movements {
        object MoveLeft : Movements()
        object MoveRight : Movements()
        object MoveForward : Movements()
        object MoveBackward : Movements()
        object RotateClockwise : Movements()
        object RotateCounterclockwise : Movements()
    }
}
