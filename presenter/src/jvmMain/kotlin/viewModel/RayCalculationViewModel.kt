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
import util.exception.ApparentVisibilityIsNullException
import util.exception.ConfigurationException
import util.exception.RayTracingConfigurationIsNullException

internal class RayCalculationViewModel(
    private val getUiRaysUseCase: GetUiRaysUseCase,
    private val setupRayTracingConfigurationUseCase: SetupRayTracingConfigurationUseCase,
    private val getDistanceToCollisionUseCase: GetDistanceToCollisionUseCase,
    private val distanceToCollisionMapper: DistanceToCollisionMapper
) {
    private val _rayList = mutableStateOf<List<Ray>>(emptyList())
    val rayList: State<List<Ray>> get() = _rayList

    private val _pointList = mutableStateOf<List<Pair<Offset, Offset>>>(emptyList())
    val pointList: State<List<Pair<Offset, Offset>>> get() = _pointList

    private var _apparentVisibility = mutableStateOf<Number?>(null)
    val apparentVisibility: State<Number?> get() = _apparentVisibility

    private var _rayTracingConfiguration = mutableStateOf<RayTracingConfiguration?>(null)
    val rayTracingConfiguration: State<RayTracingConfiguration?> get() = _rayTracingConfiguration

    private var _errorText = mutableStateOf<String?>(null)
    val errorText: State<String?> get() = _errorText

    fun setupConfiguration(
        numbersOfRay: Int,
        horizontalFov: Number,
        maxRayLength: Number,
        apparentVisibility: Number
    ) {
        this._apparentVisibility.value = apparentVisibility
        this._rayTracingConfiguration.value =
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
                            _apparentVisibility.value ?: throw ApparentVisibilityIsNullException(),
                            _rayTracingConfiguration.value ?: throw RayTracingConfigurationIsNullException()
                        )
                    _errorText.value = null
                } catch (e: Exception) {
                    handleError(e)
                }
            }
        }
    }

    fun getUiRays() {
        _rayTracingConfiguration.value?.let {
            CoroutineScope(Dispatchers.Default).launch {
                try {
                    _rayList.value = getUiRaysUseCase.execute(
                        RayTracingConfiguration(
                            it.numbersOfRay,
                            it.horizontalFov,
                            _apparentVisibility.value ?: throw ApparentVisibilityIsNullException()
                        )
                    )
                    _errorText.value = null
                } catch (e: Exception) {
                    handleError(e)
                }
            }
        }
    }

    private fun handleError(e: Exception) {
        when (e) {
            is ConfigurationException -> _errorText.value = "Configuration not installed"
            else -> _errorText.value = "Something went wrong ($e)"
        }
    }
}
