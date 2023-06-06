package viewModel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.sealedClass.LocationSample
import useCase.SetSampleLocationUseCase

internal class LocationViewModel(private val setSampleLocationUseCase: SetSampleLocationUseCase) {
    fun setSampleLocation(sample: LocationSample) {
        CoroutineScope(Dispatchers.Default).launch {
            setSampleLocationUseCase.execute(sample)
        }
    }
}
