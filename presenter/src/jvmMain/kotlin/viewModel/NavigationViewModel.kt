package viewModel

import androidx.compose.runtime.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.Point
import model.Position
import useCase.GetCurrentPositionUseCase
import useCase.GetGoalPointUseCase
import useCase.SetCurrentPositionUseCase
import useCase.SetGoalPointUseCase

internal class NavigationViewModel(
    private val setCurrentPositionUseCase: SetCurrentPositionUseCase,
    private val setGoalPointUseCase: SetGoalPointUseCase,
    getCurrentPositionUseCase: GetCurrentPositionUseCase,
    getGoalPointUseCase: GetGoalPointUseCase
) {
    private val _currentPosition = getCurrentPositionUseCase.execute()
    val currentPosition: State<Position?> get() = _currentPosition

    private val _goalPoint = getGoalPointUseCase.execute()
    val goalPoint: State<Point?> get() = _goalPoint

    fun setCurrentPosition(position: Position) {
        CoroutineScope(Dispatchers.Default).launch {
            setCurrentPositionUseCase.execute(position)
        }
    }

    fun setGoalCoordinates(goal: Point) {
        setGoalPointUseCase.execute(goal)
    }
}
