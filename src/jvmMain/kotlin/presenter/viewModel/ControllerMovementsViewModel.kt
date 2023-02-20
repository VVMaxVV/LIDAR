package presenter.viewModel

import androidx.compose.ui.geometry.Offset
import domain.model.Position
import domain.useCase.SetCurrentPositionUseCase
import domain.utils.getX
import domain.utils.getY
import domain.utils.plus

internal class ControllerMovementsViewModel(
    private val setCurrentPositionUseCase: SetCurrentPositionUseCase
) {
    private var currentPosition: Position? = null
    fun move(movements: Movements) {
        currentPosition?.let {
            when (movements) {
                is Movements.MoveLeft -> it.currentCoordinates.moveTo(
                    Offset(
                        getX(-1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                        getY(-1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                    )
                )

                is Movements.MoveRight -> it.currentCoordinates.moveTo(
                    Offset(
                        getX(1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                        getY(1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                    )
                )

                is Movements.MoveForward -> it.currentCoordinates.moveTo(
                    Offset(
                        getX(1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                        getY(1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                    )
                )

                is Movements.MoveBackward -> it.currentCoordinates.moveTo(
                    Offset(
                        getX(-1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                        getY(-1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                    )
                )

                is Movements.RotateClockwise -> it.currentTiltAngle.rotateOnXPlane(-5)
                is Movements.RotateCounterclockwise -> it.currentTiltAngle.rotateOnXPlane(5)
            }
        }
    }

    fun setCurrentPosition(position: Position) {
        currentPosition = position
        currentPosition?.let {
            setCurrentPositionUseCase.execute(it)
        }
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
