package viewModel

import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import model.Line
import model.Point
import model.Position
import model.TiltAngle
import useCase.AddSeenPointUseCase
import useCase.GetNearestObstaclesUseCase
import useCase.GetPointInterceptionUseCase
import useCase.SetCurrentPositionUseCase
import util.compareTo
import util.consts.DefaultValues
import util.getX
import util.getY
import util.isLineIntersection
import util.minus
import util.plus
import util.times

internal class ControllerMovementsViewModel(
    private val rayCalculationViewModel: RayCalculationViewModel,
    private val canvasLidarViewModel: CanvasLidarViewModel,
    private val setCurrentPositionUseCase: SetCurrentPositionUseCase,
    private val getNearestObstaclesUseCase: GetNearestObstaclesUseCase,
    private val addSeenPointUseCase: AddSeenPointUseCase,
    private val getPointInterceptionUseCase: GetPointInterceptionUseCase
) {
    private var currentPosition: Position? = null

    fun moveAlong(points: List<Point>) {
        CoroutineScope(Dispatchers.Default).launch {
            currentPosition?.let { currentPosition ->
                currentPosition.currentTiltAngle.rotateOnXPlane(currentPosition.currentTiltAngle.getAngleOnXPlane * -1)
                setCurrentPositionUseCase.execute(currentPosition)
                rayCalculationViewModel.fetchPointsInterception()
                delay(500)
                currentPosition.currentCoordinates.let { currentCoordinate ->
                    for (point in points) {
                        Thread.sleep(250)
                        if (isMovingPossible(point)) {
                            when {
                                point.x < currentCoordinate.x -> rotateUntil(TiltAngle(90))

                                point.x > currentCoordinate.x -> rotateUntil(TiltAngle(270))

                                point.y > currentCoordinate.y -> rotateUntil(TiltAngle(0))

                                point.y < currentCoordinate.y -> rotateUntil(TiltAngle(180))
                            }
                            currentCoordinate.moveTo(Offset(point.x.toFloat(), point.y.toFloat()))
                            setCurrentPositionUseCase.execute(currentPosition)
                            rayCalculationViewModel.fetchPointsInterception()
                            canvasLidarViewModel.focusableOnCanvas()
                        } else {
                            break
                        }
                    }
                }
            }
        }
    }

    fun move(movements: Movements) {
        CoroutineScope(Dispatchers.Default).launch {
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
                addSeenPointUseCase.execute(getPointInterceptionUseCase.execute())
            }
        }
    }

    fun setCurrentPosition(position: Position) {
        currentPosition = position
        setCurrentPositionUseCase.execute(position)
    }

    private fun rotateTo(position: Position, angle: TiltAngle) {
        val diff = (angle.getAngleOnXPlane - position.currentTiltAngle.getAngleOnXPlane + 360) % 360
        if (diff <= 180) {
            position.currentTiltAngle.rotateOnXPlane(10)
        } else {
            position.currentTiltAngle.rotateOnXPlane(-10)
        }
    }

    private suspend fun rotateUntil(angle: TiltAngle) {
        currentPosition?.let { currentPosition ->
            while (currentPosition.currentTiltAngle.getAngleOnXPlane.toInt() != angle.getAngleOnXPlane.toInt()) {
                rotateTo(currentPosition, angle)
                setCurrentPositionUseCase.execute(currentPosition)
                rayCalculationViewModel.fetchPointsInterception()
                delay(100)
            }
        }
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
