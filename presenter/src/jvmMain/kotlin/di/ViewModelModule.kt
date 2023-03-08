package di

import org.koin.dsl.module
import viewModel.ControllerMovementsViewModel
import viewModel.RayCalculationViewModel

internal val viewModelModule = module {
    single { RayCalculationViewModel(get(), get(), get(), get(), get()) }
    single { ControllerMovementsViewModel(get()) }
}
