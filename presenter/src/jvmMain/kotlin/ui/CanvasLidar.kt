package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import model.Point
import model.Position
import model.TiltAngle
import viewModel.ControllerMovementsViewModel
import viewModel.RayCalculationViewModel

private const val RAYS_NUMBER = 16
private const val RAYS_HORIZONTAL_FOV = 48
private const val MAX_RAY_LENGTH = 45
private const val VISIBILITY_HEIGHT = 45
private const val VISIBILITY_WIDTH = 20
private const val COLLISION_POINTS_WIDTH = 3f
private const val CANVAS_VERTICAL_PADDING = 12f
private val startPosition = Position(Point(x = 0f, y = 0f), TiltAngle(0))
private val canvasSize = Size(400f, 420f)
private val raysColor = Color.White
private val collisionPointsColor = Color.Green

class CanvasLidar(
    private val rayCalculationViewModel: RayCalculationViewModel,
    private val controllerMovementsViewModel: ControllerMovementsViewModel
) {

    @Preview
    @Composable
    fun display() {
        ConstraintLayout {
            val (canvasReference, columLabelsScaleReference) = createRefs()
            printCanvas(canvasReference, columLabelsScaleReference)
        }
    }

    @Composable
    private fun ConstraintLayoutScope.printCanvas(
        canvasReference: ConstrainedLayoutReference,
        columLabelsScaleReference: ConstrainedLayoutReference
    ) {
        val requester = remember { FocusRequester() }
        LaunchedEffect(Unit) { requester.requestFocus() }
        Canvas(
            Modifier.constrainAs(canvasReference) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start, margin = 40.dp)
            }
                .size(canvasSize.width.dp, canvasSize.height.dp)
                .background(Color.Black)
                .padding(vertical = CANVAS_VERTICAL_PADDING.dp)
                .onKeyEvent {
                    handleKeyEvent(it)
                }
                .focusRequester(requester)
                .focusable()
        ) {
            controllerMovementsViewModel.setCurrentPosition(startPosition)
            rayCalculationViewModel.setupLidarConfiguration(
                RAYS_NUMBER,
                RAYS_HORIZONTAL_FOV,
                MAX_RAY_LENGTH
            )
            printRays()
            printScaleLine()
            printIntersectionsPoints()
        }
        Column(
            Modifier.constrainAs(columLabelsScaleReference) {
                top.linkTo(canvasReference.top)
                bottom.linkTo(canvasReference.bottom, margin = 20.dp)
                end.linkTo(canvasReference.start)
            }
        ) {
            printLabelsScale()
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    private fun handleKeyEvent(keyEvent: KeyEvent): Boolean {
        when (keyEvent.key) {
            Key.A -> {
                controllerMovementsViewModel.move(ControllerMovementsViewModel.Movements.MoveLeft)
            }

            Key.D -> {
                controllerMovementsViewModel.move(ControllerMovementsViewModel.Movements.MoveRight)
            }

            Key.W -> {
                controllerMovementsViewModel.move(ControllerMovementsViewModel.Movements.MoveForward)
            }

            Key.S -> {
                controllerMovementsViewModel.move(ControllerMovementsViewModel.Movements.MoveBackward)
            }

            Key.DirectionRight -> {
                controllerMovementsViewModel.move(ControllerMovementsViewModel.Movements.RotateClockwise)
            }

            Key.DirectionLeft -> {
                controllerMovementsViewModel.move(ControllerMovementsViewModel.Movements.RotateCounterclockwise)
            }

            else -> {
                return false
            }
        }
        rayCalculationViewModel.fetchPointsInterception(
            RAYS_NUMBER,
            RAYS_HORIZONTAL_FOV,
            MAX_RAY_LENGTH,
            Size(canvasSize.height - CANVAS_VERTICAL_PADDING * 2, canvasSize.width),
            VISIBILITY_HEIGHT,
            VISIBILITY_WIDTH
        )
        return true
    }

    private fun DrawScope.printRays() {
        rayCalculationViewModel.getRays(
            RAYS_NUMBER,
            RAYS_HORIZONTAL_FOV,
            MAX_RAY_LENGTH,
            Size(canvasSize.height - CANVAS_VERTICAL_PADDING * 2, canvasSize.width),
            VISIBILITY_HEIGHT,
            VISIBILITY_WIDTH
        )
        rayCalculationViewModel.rayList.value.map {
            drawLine(
                start = it.start,
                end = it.end,
                color = raysColor
            )
        }
    }

    private fun DrawScope.printIntersectionsPoints() {
        rayCalculationViewModel.pointList.value.let { pointList ->
            drawPoints(
                points = pointList,
                pointMode = PointMode.Points,
                strokeWidth = COLLISION_POINTS_WIDTH,
                color = collisionPointsColor
            )
        }
    }

    private fun DrawScope.printScaleLine() {
        for (i in 0 until 10) {
            val dpBetweenLine = size.height / 9
            drawLine(
                start = Offset(x = 0f, y = dpBetweenLine * i),
                end = Offset(x = -8f, y = dpBetweenLine * i),
                color = Color.Black
            )
        }
    }

    @Composable
    private fun printLabelsScale() {
        Text(
            text = "45\n40\n35\n30\n25\n20\n15\n10\n 5\n 0",
            Modifier.padding(end = 10.dp),
            lineHeight = 44.sp
        )
    }
}
