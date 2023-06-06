package useCase.impl

import exception.CurrentPositionNotDefinedException
import model.Point
import model.sealedClass.Movements
import repository.CurrentPositionRepository
import repository.TrajectoryRepository
import useCase.IsMovePossibleUseCase
import useCase.MoveUseCase
import util.getX
import util.getY
import util.plus

internal class MoveUseCaseImpl(
    private val currentPositionRepository: CurrentPositionRepository,
    private val isMovePossibleUseCase: IsMovePossibleUseCase,
    private val trajectoryRepository: TrajectoryRepository
) : MoveUseCase {
    override suspend fun execute(movements: Movements) {
        (currentPositionRepository.getCurrentPosition().value ?: throw CurrentPositionNotDefinedException()).let {
            when (movements) {
                Movements.MoveLeft -> {
                    val newCoordinate = Point(
                        getX(-1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                        getY(-1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                    )
                    if (isMovePossibleUseCase.execute(newCoordinate)) {
                        currentPositionRepository.setCurrentPosition(it.setCoordinate(newCoordinate))
                    }
                }

                Movements.MoveRight -> {
                    val newCoordinate = Point(
                        getX(1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                        getY(1f, it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                    )
                    if (isMovePossibleUseCase.execute(newCoordinate)) {
                        currentPositionRepository.setCurrentPosition(it.setCoordinate(newCoordinate))
                    }
                }

                Movements.MoveForward -> {
                    val newCoordinate = Point(
                        getX(1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                        getY(1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                    )
                    if (isMovePossibleUseCase.execute(newCoordinate)) {
                        currentPositionRepository.setCurrentPosition(it.setCoordinate(newCoordinate))
                    }
                }

                Movements.MoveBackward -> {
                    val newCoordinate = Point(
                        getX(-1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.x),
                        getY(-1f, 90 + it.currentTiltAngle.getAngleOnXPlane, it.currentCoordinates.y)
                    )
                    if (isMovePossibleUseCase.execute(newCoordinate)) {
                        currentPositionRepository.setCurrentPosition(it.setCoordinate(newCoordinate))
                    }
                }

                Movements.RotateClockwise -> currentPositionRepository.setCurrentPosition(it.rotateOnXPlane(-5f))

                Movements.RotateCounterclockwise -> currentPositionRepository.setCurrentPosition(it.rotateOnXPlane(5f))
            }
            it.currentCoordinates.apply {
                trajectoryRepository.addPoint(Point(x, y))
            }
        }
    }
}
