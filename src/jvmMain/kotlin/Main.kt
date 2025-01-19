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
import ui.PlannedTrajectoryScreen

private const val WINDOW_WIDTH = 1920
private const val WINDOW_HEIGHT = 700

fun main() = application {
    uploadModules()
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(width = WINDOW_WIDTH.dp, height = WINDOW_HEIGHT.dp)
    ) {
        (get(MainWindow::class.java) as MainWindow).start()
    }
//    Window(
//        onCloseRequest = ::exitApplication,
//        state = WindowState(width = 500.dp, height = 500.dp)
//    ) {
//        PlannedTrajectoryScreen()
//    }
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
