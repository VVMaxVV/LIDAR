package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import model.Point
import model.Position
import model.TiltAngle
import model.sealedClass.LocationSample
import viewModel.LocationViewModel
import viewModel.MiniMapViewModel
import viewModel.NavigationViewModel
import viewModel.RefreshContentCanvasViewModel

internal class MiniMapMenuFragment(
    private val miniMapViewModel: MiniMapViewModel,
    private val refreshContentCanvasViewModel: RefreshContentCanvasViewModel,
    private val locationViewModel: LocationViewModel,
    private val navigationViewModel: NavigationViewModel
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
            Button(onClick = {
                locationViewModel.setSampleLocation(LocationSample.FirstSample)
                navigationViewModel.setCurrentPosition(Position(Point(0, 0), TiltAngle(0)))
                refreshContentCanvasViewModel.refreshContent()
            }) {
                Text("Set first sample location", Modifier.weight(1f), textAlign = TextAlign.Center)
            }
            Button(onClick = {
                locationViewModel.setSampleLocation(LocationSample.SecondSample)
                navigationViewModel.setCurrentPosition(Position(Point(0, 0), TiltAngle(0)))
                refreshContentCanvasViewModel.refreshContent()
            }) {
                Text("Set second sample location", Modifier.weight(1f), textAlign = TextAlign.Center)
            }
            Button(onClick = {
                locationViewModel.setSampleLocation(LocationSample.ThirdSample)
                navigationViewModel.setCurrentPosition(Position(Point(0, 0), TiltAngle(0)))
                refreshContentCanvasViewModel.refreshContent()
            }) {
                Text("Set third sample location", Modifier.weight(1f), textAlign = TextAlign.Center)
            }
        }
    }
}
