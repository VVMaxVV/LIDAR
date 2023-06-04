package viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.LineByOffset
import model.Ray
import useCase.GetObstaclesAroundUseCase
import useCase.GetRaysOnPlaneUseCase

internal class MiniMapViewModel(
    private val getObstaclesAroundUseCase: GetObstaclesAroundUseCase,
    private val getRaysOnPlaneUseCase: GetRaysOnPlaneUseCase
) {
    private var _obstaclesList = mutableStateOf<List<LineByOffset>>(emptyList())
    val obstaclesList: State<List<LineByOffset>> get() = _obstaclesList

    private val _rayListState = mutableStateOf<List<Ray>>(emptyList())
    val ratListState: State<List<Ray>> get() = _rayListState

    fun fetchObstacles() {
        CoroutineScope(Dispatchers.Default).launch {
            _obstaclesList.value = getObstaclesAroundUseCase.execute(50)
        }
    }

    fun fetchRays() {
        CoroutineScope(Dispatchers.Default).launch {
            getRaysOnPlaneUseCase.execute().collect {
                _rayListState.value = it
            }
        }
    }
}
