package viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.LineByOffset
import model.Point
import useCase.GetCurrentPositionAsOffsetUseCase
import useCase.GetObstaclesAroundUseCase

internal class MiniMapViewModel(
    private val getObstaclesAroundUseCase: GetObstaclesAroundUseCase,
    private val getCurrentPositionAsOffsetUseCase: GetCurrentPositionAsOffsetUseCase
) {
    private var _obstaclesList = mutableStateOf<List<LineByOffset>>(emptyList())
    val obstaclesList: State<List<LineByOffset>> get() = _obstaclesList

    private var _currentPosition = mutableStateOf(getCurrentPositionAsOffsetUseCase.execute())
    val currentPosition: State<Offset?> get() = _currentPosition

    fun fetchObstacles() {
        CoroutineScope(Dispatchers.Default).launch {
            _obstaclesList.value = getObstaclesAroundUseCase.execute(Point(0, 0), 50)
        }
    }

    fun fetchCurrentPosition() {
        _currentPosition.value = getCurrentPositionAsOffsetUseCase.execute()
    }
}
