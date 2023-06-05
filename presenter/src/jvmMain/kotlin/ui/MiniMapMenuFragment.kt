package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import viewModel.MiniMapViewModel
import viewModel.RefreshContentCanvasViewModel

internal class MiniMapMenuFragment(
    private val miniMapViewModel: MiniMapViewModel,
    private val refreshContentCanvasViewModel: RefreshContentCanvasViewModel
) {
    @Composable
    fun display() {
        Column {
            Row {
                Button(modifier = Modifier.weight(1f), onClick = {
                    miniMapViewModel.setTrajectoryVisibility(!miniMapViewModel.isTrajectoryVisible.value)
                }) {
                    if (miniMapViewModel.isTrajectoryVisible.value) {
                        Text("Hide trajectory")
                    } else {
                        Text("Show trajectory")
                    }
                }
                Button(modifier = Modifier.weight(1f), onClick = {
                    miniMapViewModel.cleanTrajectory()
                    refreshContentCanvasViewModel.refreshContent()
                }) {
                    Text("Clear trajectory")
                }
            }
        }
    }
}
