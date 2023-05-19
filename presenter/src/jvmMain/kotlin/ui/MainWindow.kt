package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import org.koin.java.KoinJavaComponent.inject

class MainWindow {
    private val canvasLidarFragment: CanvasLidarFragment by inject(CanvasLidarFragment::class.java)
    private val lidarParametersFragment: LidarParametersFragment by inject(LidarParametersFragment::class.java)

    @Preview
    @Composable
    fun start() {
        MaterialTheme {
            Row {
                canvasLidarFragment.display()
                lidarParametersFragment.display()
            }
        }
    }
}
