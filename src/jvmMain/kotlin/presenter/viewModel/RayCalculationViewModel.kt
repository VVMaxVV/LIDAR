package presenter.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Size
import domain.model.DetailsLidarSector
import domain.model.Ray
import domain.useCase.GetUiRaysUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import presenter.mapper.RayMapper

internal class RayCalculationViewModel(
    private val getUiRaysUseCase: GetUiRaysUseCase,
    private val rayMapper: RayMapper
) {
    private val _rayList = mutableStateOf<List<Ray>>(emptyList())
    val rayList: State<List<Ray>> get() = _rayList
    fun getRays(numbersOfRay: Int, horizontalFov: Number, maxRayLength: Number, viewSize: Size) {
        CoroutineScope(Dispatchers.Default).launch {
            getUiRaysUseCase.execute(
                DetailsLidarSector(numbersOfRay, horizontalFov, maxRayLength)
            ).also { rayList ->
                _rayList.value = rayMapper.toCanvasLines(rayList, viewSize)
            }
        }
    }
}
