package di

import org.koin.dsl.module
import viewModel.ControllerMovementsViewModel
import viewModel.MiniMapViewModel
import viewModel.NavigationViewModel
import viewModel.RayCalculationViewModel
import viewModel.RefreshContentCanvasViewModel
import viewModel.ToastViewModel

internal val viewModelModule = module {
    single { RayCalculationViewModel(get(), get(), get(), get(), get(), get()) }
    single { ControllerMovementsViewModel(get(), get()) }
    single { RefreshContentCanvasViewModel() }
    single { MiniMapViewModel(get(), get()) }
    single { NavigationViewModel(get(), get(), get(), get()) }
    single { ToastViewModel() }
}
