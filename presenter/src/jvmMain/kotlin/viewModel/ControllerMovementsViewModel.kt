package viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import model.Line
import model.Point
import model.Position
import model.TiltAngle
import useCase.AddSeenPointUseCase
import useCase.GetNearestObstaclesUseCase
import useCase.GetPathUseCase
import useCase.GetPointInterceptionUseCase
import useCase.SetCurrentPositionUseCase
import util.consts.DefaultValues
import util.equals
import util.getX
import util.getY
import util.isLineIntersection
import util.minus
import util.plus

internal class ControllerMovementsViewModel(
    private val rayCalculationViewModel: RayCalculationViewModel,
    private val canvasLidarViewModel: CanvasLidarViewModel,
    private val setCurrentPositionUseCase: SetCurrentPositionUseCase,
    private val getNearestObstaclesUseCase: GetNearestObstaclesUseCase,
    private val addSeenPointUseCase: AddSeenPointUseCase,
    private val getPointInterceptionUseCase: GetPointInterceptionUseCase,
    private val getPathUseCase: GetPathUseCase
) {
    private var currentPosition = mutableStateOf<Position?>(null)

    fun moveTo(goal: Point) {
        var isPathPossible = true
        CoroutineScope(Dispatchers.Default).launch {
            currentPosition.value?.currentCoordinates?.let { currentCoordinate ->
                while (isPathPossible) {
                    val points = getPathUseCase.execute(currentCoordinate, goal)
                    if (currentCoordinate.x.equals(points.last().x, 0.01) &&
                        currentCoordinate.y.equals(points.last().y, 0.01)
                    ) {
                        break
                    }
                    if (points.isNotEmpty()) {
                        for (point in points.drop(1)) {
                            when {
                                currentCoordinate.y - point.y <= -0.9 -> rotateUntil(TiltAngle(0))
                                currentCoordinate.x - point.x >= 0.9 -> rotateUntil(TiltAngle(90))
                                currentCoordinate.y - point.y >= 0.9 -> rotateUntil(TiltAngle(180))
                                currentCoordinate.x - point.x <= -0.9 -> rotateUntil(TiltAngle(270))
                            }
                            if (isMovingPossible(point)) {
                                move(Movements.MoveForward)
                                canvasLidarViewModel.focusableOnCanvas()
                            } else {
                                break
                            }
                            delay(100)
                        }
                    } else {
                        isPathPossible = false
                    }
                }
            }
        }
    }

    fun move(movements: Movements) {
        CoroutineScope(Dispatchers.Default).launch {
            currentPosition.value?.let {
                when (movements) {
                    Movements.MoveLeft -> {
                        val newCoordinate = Point(
                            getX(-1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                            getY(-1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                        )
                        if (isMovingPossible(newCoordinate)) {
                            currentPosition.value = it.setCoordinate(newCoordinate)
                        }
                    }

                    Movements.MoveRight -> {
                        val newCoordinate = Point(
                            getX(1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                            getY(1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                        )
                        if (isMovingPossible(newCoordinate)) {
                            currentPosition.value = it.setCoordinate(newCoordinate)
                        }
                    }

                    Movements.MoveForward -> {
                        val newCoordinate = Point(
                            getX(1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                            getY(1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                        )
                        if (isMovingPossible(newCoordinate)) {
                            currentPosition.value = it.setCoordinate(newCoordinate)
                        }
                    }

                    Movements.MoveBackward -> {
                        val newCoordinate = Point(
                            getX(-1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                            getY(-1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                        )
                        if (isMovingPossible(newCoordinate)) {
                            currentPosition.value = it.setCoordinate(newCoordinate)
                        }
                    }

                    Movements.RotateClockwise -> currentPosition.value = it.rotateOnXPlane(-5f)

                    Movements.RotateCounterclockwise -> currentPosition.value = it.rotateOnXPlane(5f)
                }
                rayCalculationViewModel.fetchPointsInterception()
            }
        }
    }

    fun setCurrentPosition(position: MutableState<Position?>) {
        CoroutineScope(Dispatchers.Default).launch {
            currentPosition = position.also {
                it.value?.let { position ->
                    setCurrentPositionUseCase.execute(position)
                }
                addSeenPointUseCase.execute(getPointInterceptionUseCase.execute())
            }
        }
    }

    private fun rotateTo(position: Position, angle: TiltAngle) {
        currentPosition.value?.let {
            val diff = (angle.getAngleOnXPlane - position.currentTiltAngle.getAngleOnXPlane + 360) % 360
            if (diff <= 180) {
                currentPosition.value = it.rotateOnXPlane(10f)
            } else {
                currentPosition.value = it.rotateOnXPlane(-10f)
            }
        }
    }

    private suspend fun rotateUntil(angle: TiltAngle) {
        currentPosition.let { positionState ->
            while (currentPosition.value?.currentTiltAngle?.getAngleOnXPlane?.toInt() != angle.getAngleOnXPlane.toInt()) {
                rotateTo(positionState.value!!, angle)
                rayCalculationViewModel.fetchPointsInterception()
                delay(100)
            }
        }
    }

    private suspend fun isMovingPossible(newPosition: Point): Boolean {
        return withContext(NonCancellable) {
            currentPosition.value?.currentCoordinates?.let { currentCoordinate ->
                getNearestObstaclesUseCase.execute(
                    currentCoordinate,
                    DefaultValues.SIZE_CHECKED_ZONE_AROUND_POSITION / 2f
                ).forEach {
                    if (isLineIntersection(Line(currentCoordinate, newPosition), it)) {
                        return@withContext false
                    }
                }
            }
            return@withContext true
        }
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
