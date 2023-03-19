package di

import org.koin.dsl.module
import viewModel.CanvasLidarViewModel
import viewModel.ControllerMovementsViewModel
import viewModel.RayCalculationViewModel

internal val viewModelModule = module {
    single { RayCalculationViewModel(get(), get(), get(), get()) }
<<<<<<< HEAD
    single { ControllerMovementsViewModel(get(), get()) }
    single { CanvasLidarViewModel() }
=======
    single { ControllerMovementsViewModel(get()) }
>>>>>>> 76a1061 (#22 Refactor CanvasLidar and View Models )
}
