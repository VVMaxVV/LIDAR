package ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import model.Point
import util.isCoordinate
import viewModel.ControllerMovementsViewModel
import viewModel.NavigationViewModel
import viewModel.ToastViewModel

private const val TEXT_COORDINATE_PADDING = 4

internal class MenuControlFragment(
    private val controllerMovementsViewModel: ControllerMovementsViewModel,
    private val navigationViewModel: NavigationViewModel,
    private val toastViewModel: ToastViewModel
) {

    @Composable
    fun display() {
        printCurrentPosition()
        printControlButtons()
        printFieldsSetGoalPosition()
        printButtonSetGoalPosition()
        handleErrors()
    }

    @Composable
    private fun printCurrentPosition() {
        val position by remember { navigationViewModel.currentPosition }
        Row {
            Text(
                text = "x: ${position?.currentCoordinates?.x?.toInt() ?: "null"}",
                modifier = Modifier.weight(1f).border(1.dp, Color.Black)
                    .padding(vertical = TEXT_COORDINATE_PADDING.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "y: ${position?.currentCoordinates?.y?.toInt() ?: "null"}",
                modifier = Modifier.weight(1f).border(1.dp, Color.Black)
                    .padding(vertical = TEXT_COORDINATE_PADDING.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Angle: ${
                position?.currentTiltAngle?.getAngleOnXPlane?.toInt()?.let { (360 - it) % 360 } ?: "null"
                }°",
                modifier = Modifier.weight(1f).border(1.dp, Color.Black)
                    .padding(vertical = TEXT_COORDINATE_PADDING.dp),
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    private fun printControlButtons() {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                controllerMovementsViewModel.buttonControlMovement()
            }
        ) {
            Text(controllerMovementsViewModel.movementTowardsGoalState.value.getTextContent())
        }
    }

    private var goalCoordinateX by mutableStateOf("")
    private var goalCoordinateY by mutableStateOf("")
    private var isGoalFieldCoordinatesXError by mutableStateOf(true)
    private var isGoalFieldCoordinatesYError by mutableStateOf(true)

    @Composable
    private fun printFieldsSetGoalPosition() {
        Row(Modifier.fillMaxWidth()) {
            TextField(
                value = goalCoordinateX,
                onValueChange = { value ->
                    goalCoordinateX = value.filter { it.isDigit() || it == '-' }
                    isGoalFieldCoordinatesXError = isCoordinate(goalCoordinateX)
                },
                modifier = Modifier.weight(1f).padding(horizontal = 20.dp),
                label = { Text("x:") },
                isError = isGoalFieldCoordinatesXError
            )
            TextField(
                value = goalCoordinateY,
                onValueChange = { value ->
                    goalCoordinateY = value.filter { it.isDigit() || it == '-' }
                    isGoalFieldCoordinatesYError = isCoordinate(goalCoordinateY)
                },
                modifier = Modifier.weight(1f).padding(horizontal = 20.dp),
                label = { Text("y:") },
                isError = isGoalFieldCoordinatesYError
            )
        }
    }

    @Composable
    private fun printButtonSetGoalPosition() {
        Button(onClick = {
            if (!isGoalFieldCoordinatesXError && !isGoalFieldCoordinatesYError) {
                navigationViewModel.setGoalCoordinates(Point(goalCoordinateX.toInt(), goalCoordinateY.toInt()))
            }
        }) {
            Text("Set goal by coordinate", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        }
    }

    @Composable
    private fun handleErrors() {
        when (controllerMovementsViewModel.event.value) {
            is ControllerMovementsViewModel.Event.GoalNotDefined -> toastViewModel.showToast("Goal point not defined")
            is ControllerMovementsViewModel.Event.PathNotExist -> toastViewModel.showToast("Path to goal not exist")
            is ControllerMovementsViewModel.Event.DestinationReached -> toastViewModel.showToast("Destination reached")
            else -> {}
        }
    }
}
