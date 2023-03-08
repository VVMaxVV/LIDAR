package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

class MainWindow(private val canvasLidar: CanvasLidar) {

    @Preview
    @Composable
    fun start() {
        MaterialTheme {
            canvasLidar.display()
        }
    }
}
