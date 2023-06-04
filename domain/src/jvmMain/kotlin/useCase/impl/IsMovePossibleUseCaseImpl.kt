package useCase.impl

import exception.CurrentPositionNotDefinedException
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import model.Line
import model.Point
import repository.CurrentPositionRepository
import useCase.GetNearestObstaclesUseCase
import useCase.IsMovePossibleUseCase
import util.isLineIntersection

internal class IsMovePossibleUseCaseImpl(
    private val currentPositionRepository: CurrentPositionRepository,
    private val getNearestObstaclesUseCase: GetNearestObstaclesUseCase
) :
    IsMovePossibleUseCase {
    override suspend fun execute(destination: Point): Boolean {
        return withContext(NonCancellable) {
            (
                currentPositionRepository.getCurrentPosition().value
                    ?: throw CurrentPositionNotDefinedException()
                ).currentCoordinates.let { currentCoordinate ->
                getNearestObstaclesUseCase.execute(
                    currentCoordinate,
                    2f
                ).forEach {
                    if (isLineIntersection(Line(currentCoordinate, destination), it)) {
                        return@withContext false
                    }
                }
            }
            return@withContext true
        }
    }
}
