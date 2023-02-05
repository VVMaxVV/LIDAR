package presener.di

import org.koin.dsl.module
import presener.ui.LidarCanvas
import presener.ui.MainWindow

val uiModule = module {
    single { MainWindow(get()) }
    single { LidarCanvas() }
}
