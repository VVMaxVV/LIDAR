package viewModel

import androidx.compose.ui.geometry.Offset
import model.Position
import useCase.SetCurrentPositionUseCase
import util.getX
import util.getY
import util.plus

internal class ControllerMovementsViewModel(
    private val setCurrentPositionUseCase: SetCurrentPositionUseCase
) {
    private var currentPosition: Position? = null
    fun move(movements: Movements) {
        currentPosition?.let {
            when (movements) {
                Movements.MoveLeft -> it.currentCoordinates.moveTo(
                    Offset(
                        getX(-1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                        getY(-1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                    )
                )

                Movements.MoveRight -> it.currentCoordinates.moveTo(
                    Offset(
                        getX(1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                        getY(1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                    )
                )

                Movements.MoveForward -> it.currentCoordinates.moveTo(
                    Offset(
                        getX(1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                        getY(1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                    )
                )

                Movements.MoveBackward -> it.currentCoordinates.moveTo(
                    Offset(
                        getX(-1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                        getY(-1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                    )
                )

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

    sealed class Movements() {
        object MoveLeft : Movements()
        object MoveRight : Movements()
        object MoveForward : Movements()
        object MoveBackward : Movements()
        object RotateClockwise : Movements()
        object RotateCounterclockwise : Movements()
    }
}
