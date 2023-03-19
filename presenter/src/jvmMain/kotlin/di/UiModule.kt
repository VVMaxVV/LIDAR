package di

import org.koin.dsl.module
import ui.CanvasLidar
import ui.LidarParametersFragment
import ui.MainWindow

internal val uiModule = module {
    single { MainWindow() }
<<<<<<< HEAD
    single { CanvasLidar(get(), get(), get()) }
    single { LidarParametersFragment(get(), get()) }
=======
    single { CanvasLidar(get(), get()) }
>>>>>>> 76a1061 (#22 Refactor CanvasLidar and View Models )
}
