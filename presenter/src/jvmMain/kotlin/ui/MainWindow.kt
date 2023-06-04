package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.java.KoinJavaComponent.inject
import util.ui.ToastNotification

class MainWindow {
    private val canvasLidarFragment: CanvasLidarFragment by inject(CanvasLidarFragment::class.java)
    private val miniMapFragment: MiniMapFragment by inject(MiniMapFragment::class.java)
    private val lidarParametersFragment: LidarParametersFragment by inject(LidarParametersFragment::class.java)
    private val menuControlFragment: MenuControlFragment by inject(MenuControlFragment::class.java)
    private val toastNotification: ToastNotification by inject(ToastNotification::class.java)

    @Preview
    @Composable
    fun start() {
        MaterialTheme {
            Box(Modifier.fillMaxSize()) {
                Row {
                    Column(Modifier.width(700.dp)) {
                        canvasLidarFragment.display()
                        menuControlFragment.display()
                    }
                    Row(Modifier.weight(1f)) {
                        miniMapFragment.display()
                        lidarParametersFragment.display()
                    }
                }
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                    toastNotification.showToast()
                }
            }
        }
    }
}
