package presener.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

internal class MainWindow(private val lidarCanvas: LidarCanvas) {

    @Preview
    @Composable
    fun start() {
        MaterialTheme {
            lidarCanvas.display()
        }
    }
}
