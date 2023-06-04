package useCase.impl

import exception.CurrentPositionNotDefinedException
import exception.GoalPointNotDefinedException
import exception.PathNotExistException
import kotlinx.coroutines.delay
import model.Movements
import model.Position
import model.TiltAngle
import repository.CurrentPositionRepository
import repository.GoalPointRepository
import useCase.GetPathUseCase
import useCase.IsMovePossibleUseCase
import useCase.MoveToGoalUseCase
import useCase.MoveUseCase
import util.minus

internal class MoveToGoalUseCaseImpl(
    private val currentPositionRepository: CurrentPositionRepository,
    private val getPathUseCase: GetPathUseCase,
    private val goalPointRepository: GoalPointRepository,
    private val isMovePossibleUseCase: IsMovePossibleUseCase,
    private val moveUseCase: MoveUseCase
) : MoveToGoalUseCase {
    override suspend fun execute() {
        (
            currentPositionRepository.getCurrentPosition().value
                ?: throw CurrentPositionNotDefinedException()
            ).let { currentPosition ->
            currentPosition.currentCoordinates.let { currentCoordinate ->
                val goal = goalPointRepository.getGoalPoint().value ?: throw GoalPointNotDefinedException()
                while (currentCoordinate.getDistance(goal) > 1) {
                    val points = getPathUseCase.execute(
                        currentCoordinate,
                        goal
                    )
                    if (points.isNotEmpty()) {
                        for (point in points.drop(1)) {
                            when {
                                currentCoordinate.y - point.y <= -0.9 -> rotateUntil(TiltAngle(0))
                                currentCoordinate.x - point.x >= 0.9 -> rotateUntil(TiltAngle(90))
                                currentCoordinate.y - point.y >= 0.9 -> rotateUntil(TiltAngle(180))
                                currentCoordinate.x - point.x <= -0.9 -> rotateUntil(TiltAngle(270))
                            }
                            if (isMovePossibleUseCase.execute(point)) {
                                moveUseCase.execute(Movements.MoveForward)
                            } else {
                                break
                            }
                            delay(100)
                        }
                    } else if (currentCoordinate.getDistance(goal) < 1) {
                        currentPositionRepository.setCurrentPosition(Position(goal, currentPosition.currentTiltAngle))
                    } else {
                        throw PathNotExistException()
                    }
                }
            }
        }
    }

    private suspend fun rotateUntil(angle: TiltAngle) {
        (currentPositionRepository.getCurrentPosition().value ?: throw CurrentPositionNotDefinedException()).let {
            while (it.currentTiltAngle.getAngleOnXPlane.toInt() != angle.getAngleOnXPlane.toInt()) {
                rotateTo(it, angle)
                delay(100)
            }
        }
    }

    private suspend fun rotateTo(position: Position, angle: TiltAngle) {
        val diff = (angle.getAngleOnXPlane - position.currentTiltAngle.getAngleOnXPlane + 360) % 360
        if (diff <= 180) {
            currentPositionRepository.setCurrentPosition(position.rotateOnXPlane(10f))
        } else {
            currentPositionRepository.setCurrentPosition(position.rotateOnXPlane(-10f))
        }
    }
}
