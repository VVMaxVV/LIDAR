import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.*
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.get
import ui.MainWindow

fun main() = application {
    uploadModules()
    Window(onCloseRequest = ::exitApplication) {
        (get(MainWindow::class.java) as MainWindow).start()
    }
}

private fun uploadModules() {
    startKoin {
        modules(
            uiModule,
            factoryModule,
            repositoryModule,
            viewModelModule,
            useCaseModule,
            mapperModule
        )
    }
}
