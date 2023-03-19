package di

import org.koin.dsl.module
import ui.CanvasLidar
import ui.MainWindow

internal val uiModule = module {
    single { MainWindow() }
    single { CanvasLidar(get(), get()) }
}
