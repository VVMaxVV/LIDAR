package presener.di

import org.koin.dsl.module
import presener.ui.CanvasLidar
import presener.ui.MainWindow

val uiModule = module {
    single { MainWindow(get()) }
    single { CanvasLidar() }
}
