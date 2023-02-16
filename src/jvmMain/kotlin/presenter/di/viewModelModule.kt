package presenter.di

import org.koin.dsl.module
import presenter.viewModel.RayCalculationViewModel

val viewModelModule = module {
    single { RayCalculationViewModel(get(), get(), get(), get(), get()) }
}
