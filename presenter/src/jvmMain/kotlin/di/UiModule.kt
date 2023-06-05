package di

import org.koin.dsl.module
import ui.CanvasLidarFragment
import ui.LidarParametersFragment
import ui.MainWindow
import ui.MenuControlFragment
import ui.MiniMapFragment
import ui.MiniMapMenuFragment
import util.ui.ToastNotification

internal val uiModule = module {
    single { MainWindow() }
    single { CanvasLidarFragment(get(), get(), get(), get()) }
    single { LidarParametersFragment(get(), get()) }
    single { MiniMapFragment(get(), get(), get()) }
    single { MenuControlFragment(get(), get(), get()) }
    single { ToastNotification(get()) }
    single { MiniMapMenuFragment(get(), get()) }
}
