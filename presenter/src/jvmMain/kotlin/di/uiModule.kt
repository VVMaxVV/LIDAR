package di

import org.koin.dsl.module
import ui.CanvasLidar
import ui.MainWindow

val uiModule = module {
    single { MainWindow(get()) }
    single { CanvasLidar(get(), get()) }
}
