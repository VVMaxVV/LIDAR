package viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.LineByOffset
import model.Ray
import ui.MiniMapFragment.Companion.SCALE_FACTOR
import useCase.ClearTrajectoryUseCase
import useCase.GetObstaclesAroundUseCase
import useCase.GetPlanedTrajectoryUseCase
import useCase.GetRaysOnPlaneUseCase
import useCase.GetTrajectoryUseCase

internal class MiniMapViewModel(
    private val getObstaclesAroundUseCase: GetObstaclesAroundUseCase,
    private val getRaysOnPlaneUseCase: GetRaysOnPlaneUseCase,
    getTrajectoryUseCase: GetTrajectoryUseCase,
    getPlanedTrajectoryUseCase: GetPlanedTrajectoryUseCase,
    private val clearTrajectoryUseCase: ClearTrajectoryUseCase
) {
    private var _obstaclesList = mutableStateOf<List<LineByOffset>>(emptyList())
    val obstaclesList: State<List<LineByOffset>> get() = _obstaclesList

    private val _rayListState = mutableStateOf<List<Ray>>(emptyList())
    val rayListState: State<List<Ray>> get() = _rayListState

    val trajectory = getTrajectoryUseCase.execute()
    val plannedTrajectory = getPlanedTrajectoryUseCase.execute()

    private val _isTrajectoryVisible = mutableStateOf(true)
    val isTrajectoryVisible: State<Boolean> get() = _isTrajectoryVisible

    private val _isPlannedTrajectoryVisible = mutableStateOf(true)
    val isPlannedTrajectoryVisible: State<Boolean> get() = _isPlannedTrajectoryVisible

    fun fetchObstacles() {
        CoroutineScope(Dispatchers.Default).launch {
            _obstaclesList.value = getObstaclesAroundUseCase.execute(200 / SCALE_FACTOR.toInt() * 2)
        }
    }

    fun fetchRays() {
        CoroutineScope(Dispatchers.Default).launch {
            getRaysOnPlaneUseCase.execute().collect {
                _rayListState.value = it
            }
        }
    }

    fun setTrajectoryVisibility(isTrajectoryVisible: Boolean) {
        _isTrajectoryVisible.value = isTrajectoryVisible
    }

    fun cleanTrajectory() {
        CoroutineScope(Dispatchers.IO).launch {
            clearTrajectoryUseCase.execute()
        }
    }
}
