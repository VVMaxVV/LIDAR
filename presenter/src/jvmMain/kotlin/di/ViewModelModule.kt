package di

import org.koin.dsl.module
import viewModel.CanvasLidarViewModel
import viewModel.ControllerMovementsViewModel
import viewModel.NavigationViewModel
import viewModel.RayCalculationViewModel

internal val viewModelModule = module {
    single { RayCalculationViewModel(get(), get(), get(), get()) }
    single { ControllerMovementsViewModel(get(), get(), get(), get(), get(), get()) }
    single { CanvasLidarViewModel() }
    single { NavigationViewModel(get()) }
}
