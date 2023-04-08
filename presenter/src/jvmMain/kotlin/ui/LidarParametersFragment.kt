package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import viewModel.CanvasLidarViewModel
import viewModel.RayCalculationViewModel

private const val DEFAULT_NUMBER_OF_RAYS = 16
private const val DEFAULT_FOV = 48
private const val DEFAULT_VISIBLE_RAY_LENGTH = 45
private const val DEFAULT_MEASURED_RAY_LENGTH = 45

internal class LidarParametersFragment(
    private val rayCalculationViewModel: RayCalculationViewModel,
    private val canvasLidarViewModel: CanvasLidarViewModel
) {
    @Composable
    fun display() {
        var numberOfRays by rememberSaveable { mutableStateOf<Number>(DEFAULT_NUMBER_OF_RAYS) }
        var fovCounter by rememberSaveable { mutableStateOf<Number>(DEFAULT_FOV) }
        var visibleRayLength by rememberSaveable { mutableStateOf<Number>(DEFAULT_VISIBLE_RAY_LENGTH) }
        var measuredRayLength by rememberSaveable { mutableStateOf<Number>(DEFAULT_MEASURED_RAY_LENGTH) }
        Column(modifier = Modifier.padding(horizontal = 32.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 16.dp)) {
                Text("Number of rays", Modifier.weight(1f))
                Counter(
                    startValue = DEFAULT_NUMBER_OF_RAYS,
                    minValue = 1,
                    maxValue = 32,
                    step = 1,
                    modifier = Modifier.width(250.dp).padding(horizontal = 12.dp)
                ) {
                    numberOfRays = it
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 16.dp)) {
                Text("Fov", Modifier.weight(1f))
                Counter(
                    startValue = DEFAULT_FOV,
                    minValue = 16,
                    maxValue = 160,
                    step = 16,
                    modifier = Modifier.width(250.dp).padding(horizontal = 12.dp)
                ) {
                    fovCounter = it
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 16.dp)) {
                Text("Visible ray length", Modifier.weight(1f))
                Counter(
                    startValue = DEFAULT_VISIBLE_RAY_LENGTH,
                    minValue = 0,
                    maxValue = 150,
                    step = 1,
                    numberFormat = NumberFormat.Decimal(2),
                    modifier = Modifier.width(250.dp).padding(horizontal = 12.dp)
                ) {
                    visibleRayLength = it
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 16.dp)) {
                Text("Measured ray length", Modifier.weight(1f))
                Counter(
                    startValue = DEFAULT_MEASURED_RAY_LENGTH,
                    minValue = 0,
                    maxValue = 150,
                    step = 1,
                    numberFormat = NumberFormat.Decimal(2),
                    modifier = Modifier.width(250.dp).padding(horizontal = 12.dp)
                ) {
                    measuredRayLength = it
                }
            }
            Button(onClick = {
                rayCalculationViewModel.setupConfiguration(
                    numberOfRays.toInt(),
                    fovCounter,
                    measuredRayLength,
                    visibleRayLength
                )
                canvasLidarViewModel.focusableOnCanvas()
            }) {
                Text(text = "Set config", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }
        }
    }
}
