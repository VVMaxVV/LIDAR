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
import util.consts.DefaultValues
import viewModel.RayCalculationViewModel
import viewModel.RefreshContentCanvasViewModel

private const val COUNTER_WIDTH = 250
private const val COUNTER_HORIZONTAL_PADDING = 12
private const val COUNTER_ITEM_VERTICAL_PADDING = 16
private const val ROOT_HORIZONTAL_PADDING = 32

private const val NUM_RAY_COUNTER_MIN_VALUE = 1
private const val NUM_RAY_COUNTER_MAX_VALUE = 32
private const val NUM_RAY_COUNTER_STEP_VALUE = 1

private const val HORIZONTAL_FOV_COUNTER_MIN_VALUE = 16
private const val HORIZONTAL_FOV_COUNTER_MAX_VALUE = 160
private const val HORIZONTAL_FOV_COUNTER_STEP_VALUE = 16

private const val APPARENT_LENGTH_COUNTER_MIN_VALUE = 0
private const val APPARENT_LENGTH_COUNTER_MAX_VALUE = 150
private const val APPARENT_LENGTH_COUNTER_STEP_VALUE = 1

private const val MEASURED_LENGTH_COUNTER_MIN_VALUE = 0
private const val MEASURED_LENGTH_COUNTER_MAX_VALUE = 150
private const val MEASURED_LENGTH_COUNTER_STEP_VALUE = 1

private const val COUNTER_DESCRIPTION_MAX_LINE = 2

internal class LidarParametersFragment(
    private val rayCalculationViewModel: RayCalculationViewModel,
    private val refreshContentCanvasViewModel: RefreshContentCanvasViewModel
) {
    @Composable
    fun display() {
        var numberOfRays by rememberSaveable { mutableStateOf<Number>(DefaultValues.DEFAULT_NUMBER_OF_RAYS) }
        var fovCounter by rememberSaveable { mutableStateOf<Number>(DefaultValues.DEFAULT_HORIZONTAL_FOV) }
        var visibleRayLength by rememberSaveable { mutableStateOf<Number>(DefaultValues.DEFAULT_APPARENT_RAY_LENGTH) }
        var measuredRayLength by rememberSaveable { mutableStateOf<Number>(DefaultValues.DEFAULT_MEASURE_RAY_LENGTH) }
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = COUNTER_ITEM_VERTICAL_PADDING.dp)
            ) {
                Text("Number of rays", Modifier.weight(1f), maxLines = COUNTER_DESCRIPTION_MAX_LINE)
                Counter(
                    startValue = DefaultValues.DEFAULT_NUMBER_OF_RAYS,
                    minValue = NUM_RAY_COUNTER_MIN_VALUE,
                    maxValue = NUM_RAY_COUNTER_MAX_VALUE,
                    step = NUM_RAY_COUNTER_STEP_VALUE,
                    modifier = Modifier.width(COUNTER_WIDTH.dp).padding(horizontal = COUNTER_HORIZONTAL_PADDING.dp)
                ) {
                    numberOfRays = it
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = COUNTER_ITEM_VERTICAL_PADDING.dp)
            ) {
                Text("Fov", Modifier.weight(1f), maxLines = COUNTER_DESCRIPTION_MAX_LINE)
                Counter(
                    startValue = DefaultValues.DEFAULT_HORIZONTAL_FOV,
                    minValue = HORIZONTAL_FOV_COUNTER_MIN_VALUE,
                    maxValue = HORIZONTAL_FOV_COUNTER_MAX_VALUE,
                    step = HORIZONTAL_FOV_COUNTER_STEP_VALUE,
                    modifier = Modifier.width(COUNTER_WIDTH.dp).padding(horizontal = COUNTER_HORIZONTAL_PADDING.dp)
                ) {
                    fovCounter = it
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = COUNTER_ITEM_VERTICAL_PADDING.dp)
            ) {
                Text("Visible ray length", Modifier.weight(1f), maxLines = COUNTER_DESCRIPTION_MAX_LINE)
                Counter(
                    startValue = DefaultValues.DEFAULT_APPARENT_RAY_LENGTH,
                    minValue = APPARENT_LENGTH_COUNTER_MIN_VALUE,
                    maxValue = APPARENT_LENGTH_COUNTER_MAX_VALUE,
                    step = APPARENT_LENGTH_COUNTER_STEP_VALUE,
                    numberFormat = NumberFormat.Decimal(2),
                    modifier = Modifier.width(COUNTER_WIDTH.dp).padding(horizontal = COUNTER_HORIZONTAL_PADDING.dp)
                ) {
                    visibleRayLength = it
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = COUNTER_ITEM_VERTICAL_PADDING.dp)
            ) {
                Text("Measured ray length", Modifier.weight(1f), maxLines = COUNTER_DESCRIPTION_MAX_LINE)
                Counter(
                    startValue = DefaultValues.DEFAULT_MEASURE_RAY_LENGTH,
                    minValue = MEASURED_LENGTH_COUNTER_MIN_VALUE,
                    maxValue = MEASURED_LENGTH_COUNTER_MAX_VALUE,
                    step = MEASURED_LENGTH_COUNTER_STEP_VALUE,
                    numberFormat = NumberFormat.Decimal(2),
                    modifier = Modifier.width(COUNTER_WIDTH.dp).padding(horizontal = COUNTER_HORIZONTAL_PADDING.dp)
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
                refreshContentCanvasViewModel.refreshContent()
            }) {
                Text(text = "Set config", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            }
        }
    }
}
