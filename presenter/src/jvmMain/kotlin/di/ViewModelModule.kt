package di

import org.koin.dsl.module
import viewModel.ControllerMovementsViewModel
import viewModel.MiniMapViewModel
import viewModel.RayCalculationViewModel
import viewModel.RefreshContentCanvasViewModel

internal val viewModelModule = module {
    single { RayCalculationViewModel(get(), get(), get(), get()) }
    single { ControllerMovementsViewModel(get(), get(), get(), get(), get(), get(), get()) }
    single { RefreshContentCanvasViewModel() }
    single { MiniMapViewModel(get(), get()) }
}
