package repository

import androidx.compose.runtime.State
import model.Point

interface GoalPointRepository {
    fun setGoalPoint(goal: Point)

    fun getGoalPoint(): State<Point?>
}
