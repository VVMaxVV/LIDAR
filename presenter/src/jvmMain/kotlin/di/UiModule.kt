package di

import org.koin.dsl.module
import ui.CanvasLidarFragment
import ui.LidarParametersFragment
import ui.MainWindow
import ui.MiniMapFragment

internal val uiModule = module {
    single { MainWindow() }
    single { CanvasLidarFragment(get(), get(), get()) }
    single { LidarParametersFragment(get(), get()) }
    single { MiniMapFragment(get(), get()) }
}
