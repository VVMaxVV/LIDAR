package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import model.Point
import model.Position
import model.TiltAngle
import util.getX
import viewModel.ControllerMovementsViewModel
import viewModel.RayCalculationViewModel

private const val NUM_RAYS = 16
private const val HORIZONTAL_FOV = 48
private const val MAX_RAY_LENGTH = 45
private const val APPARENT_RAY_LENGTH = 45
private const val COLLISION_POINTS_SIZE = .5f
private const val CANVAS_VERTICAL_PADDING = 12f
private val startPosition = Position(Point(x = 0f, y = 0f), TiltAngle(0))
private val canvasSize = Size(400f, 420f)
private val raysColor = Color.White
private val collisionPointsColor = Color.Green

internal class CanvasLidar(
    private val rayCalculationViewModel: RayCalculationViewModel,
    private val controllerMovementsViewModel: ControllerMovementsViewModel
) {
    private var viewSizeState by mutableStateOf<Size?>(null)

    @Preview
    @Composable
    fun display() {
        ConstraintLayout {
            val (canvasReference) = createRefs()
            printCanvas(canvasReference)
        }
    }

    @Composable
    private fun ConstraintLayoutScope.printCanvas(
        canvasReference: ConstrainedLayoutReference
    ) {
        val requester = remember { FocusRequester() }
        LaunchedEffect(Unit) { requester.requestFocus() }
        rayCalculationViewModel.apply {
            setupConfiguration(
                NUM_RAYS,
                HORIZONTAL_FOV,
                MAX_RAY_LENGTH,
                APPARENT_RAY_LENGTH
            )
        }
        controllerMovementsViewModel.setCurrentPosition(startPosition)
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
                .onSizeChanged {
                    viewSizeState = it.toSize()
                    rayCalculationViewModel.fetchPointsInterception()
                }
                .graphicsLayer {
                    rotationY = 180f
                    rotationZ = 180f
                    viewSizeState?.let {
                        scaleY = (it.height - CANVAS_VERTICAL_PADDING).div(APPARENT_RAY_LENGTH)
                        translationY =
                            -(it.height / 2 - APPARENT_RAY_LENGTH / 2).times(scaleY) + CANVAS_VERTICAL_PADDING / 2
                        scaleX = (it.width / 2).div(getX(APPARENT_RAY_LENGTH, 90 - HORIZONTAL_FOV / 2))
                        translationX = (it.width / -2).times(scaleX)
                    }
                }
        ) {
            printRays()
            printIntersectionsPoints()
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
        rayCalculationViewModel.fetchPointsInterception()
        return true
    }

    private fun DrawScope.printRays() {
        rayCalculationViewModel.getUiRays()
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
                strokeWidth = COLLISION_POINTS_SIZE,
                color = collisionPointsColor
            )
        }
    }
}
