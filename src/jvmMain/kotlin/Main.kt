import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import di.dataModule
import di.domainModule
import di.presenterModule
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.get
import ui.MainWindow

fun main() = application {
    uploadModules()
    Window(onCloseRequest = ::exitApplication, state = WindowState(width = 1000.dp)) {
        (get(MainWindow::class.java) as MainWindow).start()
    }
}

private fun uploadModules() {
    startKoin {
        modules(
            dataModule,
            domainModule,
            presenterModule
        )
    }
}
