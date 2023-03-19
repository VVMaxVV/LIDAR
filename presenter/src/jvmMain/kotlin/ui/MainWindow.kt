package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import org.koin.java.KoinJavaComponent.inject

class MainWindow {
    private val canvasLidar: CanvasLidar by inject(CanvasLidar::class.java)

    @Preview
    @Composable
    fun start() {
        MaterialTheme {
            canvasLidar.display()
        }
    }
}
