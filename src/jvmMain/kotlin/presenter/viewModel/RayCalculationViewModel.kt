package presenter.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import domain.model.Position
import domain.model.Ray
import domain.model.RayTracingConfiguration
import domain.useCase.GetDistanceToCollisionUseCase
import domain.useCase.GetUiRaysUseCase
import domain.useCase.SetupLidarConfigurationUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import presenter.mapper.DistanceToCollisionMapper
import presenter.mapper.RayMapper

internal class RayCalculationViewModel(
    private val getUiRaysUseCase: GetUiRaysUseCase,
    private val setupLidarConfigurationUseCase: SetupLidarConfigurationUseCase,
    private val getDistanceToCollisionUseCase: GetDistanceToCollisionUseCase,
    private val rayMapper: RayMapper,
    private val distanceToCollisionMapper: DistanceToCollisionMapper
) {
    private val _rayList = mutableStateOf<List<Ray>>(emptyList())
    val rayList: State<List<Ray>> get() = _rayList

    private val _pointList = mutableStateOf<List<Offset>>(emptyList())
    val pointList: State<List<Offset>> get() = _pointList

    fun setupLidarConfiguration(
        numbersOfRay: Int,
        horizontalFov: Number,
        maxRayLength: Number,
        currentPosition: Position
    ) {
        setupLidarConfigurationUseCase.execute(
            rayTracingConfiguration = RayTracingConfiguration(numbersOfRay, horizontalFov, maxRayLength),
            currentPosition = currentPosition
        )
    }

    fun getRays(
        numbersOfRay: Int,
        horizontalFov: Number,
        maxRayLength: Number,
        viewSize: Size,
        uiLengthVisibility: Number,
        uiWidthVisibility: Number
    ) {
        CoroutineScope(Dispatchers.Default).launch {
            val rayTracingConfiguration = RayTracingConfiguration(numbersOfRay, horizontalFov, maxRayLength)
            getUiRaysUseCase.execute(
                rayTracingConfiguration
            ).also { rayList ->
                _rayList.value = rayMapper.toView(rayList, uiLengthVisibility, uiWidthVisibility, viewSize)
            }
            getDistanceToCollisionUseCase.execute().also {
                _pointList.value =
                    distanceToCollisionMapper.toOffsetsOnView(
                        it,
                        uiLengthVisibility,
                        uiWidthVisibility,
                        rayTracingConfiguration,
                        viewSize
                    )
            }
        }
    }
}
