package presenter.di

import org.koin.dsl.module
import presenter.ui.CanvasLidar
import presenter.ui.MainWindow

val uiModule = module {
    single { MainWindow(get()) }
    single { CanvasLidar(get()) }
}
