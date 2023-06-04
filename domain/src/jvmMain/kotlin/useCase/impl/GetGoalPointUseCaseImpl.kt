package useCase.impl

import androidx.compose.runtime.State
import model.Point
import repository.GoalPointRepository
import useCase.GetGoalPointUseCase

internal class GetGoalPointUseCaseImpl(private val goalPointRepository: GoalPointRepository) : GetGoalPointUseCase {
    override fun execute(): State<Point?> = goalPointRepository.getGoalPoint()
}
