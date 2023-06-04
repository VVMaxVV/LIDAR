package useCase

import model.Point

interface SetGoalPointUseCase {
    fun execute(goal: Point)
}
