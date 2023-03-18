package viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mapper.DistanceToCollisionMapper
import mapper.RayMapper
import model.Ray
import model.RayTracingConfiguration
import useCase.GetDistanceToCollisionUseCase
import useCase.GetUiRaysUseCase
import useCase.SetupRayTracingConfigurationUseCase
import util.div
import util.exception.ConfigurationException
import util.exception.MaxVisibilityIsNullException
import util.exception.RayTracingConfigurationIsNullException
import util.exception.ViewSizeIsNullException
import util.getX

class RayCalculationViewModel(
    private val getUiRaysUseCase: GetUiRaysUseCase,
    private val setupRayTracingConfigurationUseCase: SetupRayTracingConfigurationUseCase,
    private val getDistanceToCollisionUseCase: GetDistanceToCollisionUseCase,
    private val rayMapper: RayMapper,
    private val distanceToCollisionMapper: DistanceToCollisionMapper
) {
    private val _rayList = mutableStateOf<List<Ray>>(emptyList())
    val rayList: State<List<Ray>> get() = _rayList

    private val _pointList = mutableStateOf<List<Offset>>(emptyList())
    val pointList: State<List<Offset>> get() = _pointList

    private var viewSize: Size? = null
    private var maxVisibility: Number? = null
    private var rayTracingConfiguration: RayTracingConfiguration? = null

    fun setupConfiguration(
        numbersOfRay: Int,
        horizontalFov: Number,
        maxRayLength: Number,
        maxVisibility: Number,
        viewSize: Size
    ) {
        this.viewSize = viewSize
        this.maxVisibility = maxVisibility
        this.rayTracingConfiguration =
            RayTracingConfiguration(numbersOfRay, horizontalFov, maxRayLength).also { rayTracingConfiguration ->
                setupRayTracingConfigurationUseCase.execute(rayTracingConfiguration)
            }
    }

    fun fetchPointsInterception() {
        CoroutineScope(Dispatchers.Default).launch {
            getDistanceToCollisionUseCase.execute().also {
                try {
                    _pointList.value =
                        distanceToCollisionMapper.toOffsetsOnView(
                            it,
                            maxVisibility ?: throw MaxVisibilityIsNullException(),
                            rayTracingConfiguration ?: throw RayTracingConfigurationIsNullException(),
                            viewSize ?: throw ViewSizeIsNullException()
                        )
                } catch (e: ConfigurationException) {
                    TODO("SHOW MESSAGE ABOUT ERROR")
                }
            }
        }
    }

    fun getUiRays(
        numbersOfRay: Int,
        horizontalFov: Number,
        maxRayLength: Number,
        viewSize: Size
    ) {
        val maxLateralDeviation = getX(maxRayLength, 90 - horizontalFov / 2)
        CoroutineScope(Dispatchers.Default).launch {
            getUiRaysUseCase.execute(
                RayTracingConfiguration(numbersOfRay, horizontalFov, maxRayLength)
            ).also { rayList ->
                _rayList.value = rayMapper.toView(rayList, maxRayLength, maxLateralDeviation, viewSize)
            }
        }
    }
}
