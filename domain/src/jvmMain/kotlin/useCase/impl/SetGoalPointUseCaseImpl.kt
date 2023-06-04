package useCase.impl

import model.Point
import repository.GoalPointRepository
import useCase.SetGoalPointUseCase

internal class SetGoalPointUseCaseImpl(private val goalPointRepository: GoalPointRepository) : SetGoalPointUseCase {
    override fun execute(goal: Point) {
        goalPointRepository.setGoalPoint(goal)
    }
}
