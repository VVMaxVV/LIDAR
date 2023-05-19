package di

import org.koin.dsl.module
import ui.CanvasLidarFragment
import ui.LidarParametersFragment
import ui.MainWindow

internal val uiModule = module {
    single { MainWindow() }
    single { CanvasLidarFragment(get(), get(), get()) }
    single { LidarParametersFragment(get(), get()) }
}
