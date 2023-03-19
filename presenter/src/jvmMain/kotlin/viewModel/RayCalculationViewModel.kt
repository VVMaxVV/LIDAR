package viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mapper.DistanceToCollisionMapper
import model.Ray
import model.RayTracingConfiguration
import useCase.GetDistanceToCollisionUseCase
import useCase.GetUiRaysUseCase
import useCase.SetupRayTracingConfigurationUseCase
import util.exception.ConfigurationException
import util.exception.MaxVisibilityIsNullException
import util.exception.RayTracingConfigurationIsNullException

internal class RayCalculationViewModel(
    private val getUiRaysUseCase: GetUiRaysUseCase,
    private val setupRayTracingConfigurationUseCase: SetupRayTracingConfigurationUseCase,
    private val getDistanceToCollisionUseCase: GetDistanceToCollisionUseCase,
    private val distanceToCollisionMapper: DistanceToCollisionMapper
) {
    private val _rayList = mutableStateOf<List<Ray>>(emptyList())
    val rayList: State<List<Ray>> get() = _rayList

    private val _pointList = mutableStateOf<List<Offset>>(emptyList())
    val pointList: State<List<Offset>> get() = _pointList

    private var maxVisibility: Number? = null
    private var rayTracingConfiguration: RayTracingConfiguration? = null

    fun setupConfiguration(
        numbersOfRay: Int,
        horizontalFov: Number,
        maxRayLength: Number,
        maxVisibility: Number
    ) {
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
                            rayTracingConfiguration ?: throw RayTracingConfigurationIsNullException()
                        )
                } catch (e: ConfigurationException) {
                    TODO("SHOW MESSAGE ABOUT ERROR")
                }
            }
        }
    }

    fun getUiRays() {
        rayTracingConfiguration?.let {
            CoroutineScope(Dispatchers.Default).launch {
                _rayList.value = getUiRaysUseCase.execute(
                    RayTracingConfiguration(it.numbersOfRay, it.horizontalFov, it.maxLength)
                )
            }
        }
    }
}
