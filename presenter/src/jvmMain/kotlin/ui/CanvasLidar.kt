package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import model.Point
import model.Position
import model.TiltAngle
import util.MeasureUnconstrainedViewHeight
import util.MeasureUnconstrainedViewWidth
import util.getX
import viewModel.ControllerMovementsViewModel
import viewModel.RayCalculationViewModel

private const val NUM_RAYS = 16
private const val HORIZONTAL_FOV = 48
private const val MAX_RAY_LENGTH = 45
private const val APPARENT_RAY_LENGTH = 45
private const val COLLISION_POINTS_SIZE = .5f
private const val CANVAS_VERTICAL_PADDING = 12f
private const val CANVAS_HORIZONTAL_PADDING = 12f
private const val NUM_VERTICAL_SCALE_LINES = 10
private const val NUM_HORIZONTAL_SCALE_LINES = 5
private const val RULER_FONT_SIZE = 16
private val startPosition = Position(Point(x = 0f, y = 0f), TiltAngle(0))
private val canvasSize = Size(400f, 420f)
private val raysColor = Color.White
private val collisionPointsColor = Color.Green

internal class CanvasLidar(
    private val rayCalculationViewModel: RayCalculationViewModel,
    private val controllerMovementsViewModel: ControllerMovementsViewModel
) {
    private var canvasViewSizeState by mutableStateOf<Size?>(null)

    @Preview
    @Composable
    fun display() {
        ConstraintLayout {
            val (canvasReference, verticalCanvasRulerReference, horizontalCanvasRulerReference) = createRefs()
            printVerticalCanvasRulerLabel(verticalCanvasRulerReference, canvasReference)
            printHorizontalCanvasRulerLabel(horizontalCanvasRulerReference, canvasReference)
            printCanvas(canvasReference)
        }
    }

    @Composable
    private fun ConstraintLayoutScope.printCanvas(
        canvasReference: ConstrainedLayoutReference
    ) {
        val requester = remember { FocusRequester() }
        LaunchedEffect(Unit) { requester.requestFocus() }
        setupConfigurationViewModels()
        Canvas(
            Modifier.constrainAs(canvasReference) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start, margin = 64.dp)
            }
                .size(canvasSize.width.dp, canvasSize.height.dp)
                .background(Color.Black)
                .padding(vertical = CANVAS_VERTICAL_PADDING.dp, horizontal = CANVAS_HORIZONTAL_PADDING.dp)
                .onKeyEvent {
                    handleKeyEvent(it)
                }
                .focusRequester(requester)
                .focusable()
                .onSizeChanged {
                    canvasViewSizeState = it.toSize()
                    rayCalculationViewModel.fetchPointsInterception()
                }
                .graphicsLayer {
                    rotationY = 180f
                    rotationZ = 180f
                    canvasViewSizeState?.let {
                        scaleY = it.height.div(APPARENT_RAY_LENGTH)
                        translationY =
                            -(it.height / 2 - APPARENT_RAY_LENGTH / 2).times(scaleY) + CANVAS_VERTICAL_PADDING / 2
                        scaleX = (it.width / 2).div(getX(APPARENT_RAY_LENGTH, 90 - HORIZONTAL_FOV / 2))
                        translationX = (it.width / -2).times(scaleX)
                    }
                }
        ) {
            drawHorizontalRulerLines()
            drawVerticalRulerLines()
            printRays()
            printIntersectionsPoints()
        }
    }

    private fun DrawScope.drawHorizontalRulerLines() {
        val halfSectorWidth = getX(APPARENT_RAY_LENGTH, 90 - HORIZONTAL_FOV / 2)
        val lineStartYCoordinate = -CANVAS_VERTICAL_PADDING * 2 * APPARENT_RAY_LENGTH / size.height
        val lineEndYCoordinate = -CANVAS_VERTICAL_PADDING * APPARENT_RAY_LENGTH / size.height
        drawIntoCanvas {
            it.nativeCanvas.apply {
                repeat(NUM_HORIZONTAL_SCALE_LINES) { i ->
                    val lineXCoordinate =
                        (-halfSectorWidth).plus((halfSectorWidth * 2 / (NUM_HORIZONTAL_SCALE_LINES - 1)) * i)
                    drawIntoCanvas {
                        drawLine(
                            Color.Black,
                            Offset(lineXCoordinate, lineStartYCoordinate),
                            Offset(lineXCoordinate, lineEndYCoordinate)
                        )
                    }
                }
            }
        }
    }

    private fun DrawScope.drawVerticalRulerLines() {
        val halfSectorWidth = getX(APPARENT_RAY_LENGTH, 90 - HORIZONTAL_FOV / 2)
        val lineStartXCoordinate =
            (-halfSectorWidth).minus((halfSectorWidth * 2 / size.width) * CANVAS_HORIZONTAL_PADDING * 2)
        val lineEndXCoordinate =
            (-halfSectorWidth).minus((halfSectorWidth * 2 / size.width) * CANVAS_HORIZONTAL_PADDING)
        drawIntoCanvas {
            it.nativeCanvas.apply {
                repeat(NUM_VERTICAL_SCALE_LINES) { i ->
                    drawIntoCanvas {
                        val lineYCoordinate = APPARENT_RAY_LENGTH / (NUM_VERTICAL_SCALE_LINES - 1f) * i
                        drawLine(
                            Color.Black,
                            Offset(lineStartXCoordinate, lineYCoordinate),
                            Offset(lineEndXCoordinate, lineYCoordinate)
                        )
                    }
                }
            }
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

    private fun setupConfigurationViewModels() {
        rayCalculationViewModel.apply {
            setupConfiguration(
                NUM_RAYS,
                HORIZONTAL_FOV,
                MAX_RAY_LENGTH,
                APPARENT_RAY_LENGTH
            )
        }
        controllerMovementsViewModel.setCurrentPosition(startPosition)
    }

    @Composable
    private fun ConstraintLayoutScope.printVerticalCanvasRulerLabel(
        verticalCanvasRulerReference: ConstrainedLayoutReference,
        canvasReference: ConstrainedLayoutReference
    ) {
        canvasViewSizeState?.let { size ->
            Box(
                Modifier.constrainAs(verticalCanvasRulerReference) {
                    top.linkTo(canvasReference.top, margin = CANVAS_VERTICAL_PADDING.dp)
                    end.linkTo(canvasReference.start, margin = CANVAS_HORIZONTAL_PADDING.dp)
                    bottom.linkTo(canvasReference.bottom, margin = CANVAS_VERTICAL_PADDING.dp)
                    width = Dimension.wrapContent
                    height = Dimension.fillToConstraints
                }
            ) {
                repeat(NUM_VERTICAL_SCALE_LINES) { i ->
                    MeasureUnconstrainedViewHeight(viewToMeasure = {
                        Text(
                            text = "${(APPARENT_RAY_LENGTH / (NUM_VERTICAL_SCALE_LINES - 1f) * (NUM_VERTICAL_SCALE_LINES - i - 1)).toInt()}m",
                            fontSize = RULER_FONT_SIZE.sp
                        )
                    }) {
                        Text(
                            text = "${(APPARENT_RAY_LENGTH / (NUM_VERTICAL_SCALE_LINES - 1f) * (NUM_VERTICAL_SCALE_LINES - i - 1)).toInt()}m",
                            modifier = Modifier.offset(
                                0.dp,
                                (size.height / (NUM_VERTICAL_SCALE_LINES - 1) * i - it.value / 2).dp
                            ),
                            fontSize = RULER_FONT_SIZE.sp
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun ConstraintLayoutScope.printHorizontalCanvasRulerLabel(
        horizontalCanvasRulerReference: ConstrainedLayoutReference,
        canvasReference: ConstrainedLayoutReference
    ) {
        canvasViewSizeState?.let { size ->
            Box(
                Modifier.constrainAs(horizontalCanvasRulerReference) {
                    top.linkTo(canvasReference.bottom, margin = CANVAS_VERTICAL_PADDING.dp)
                    start.linkTo(canvasReference.start, margin = CANVAS_HORIZONTAL_PADDING.dp)
                    end.linkTo(canvasReference.end, margin = CANVAS_HORIZONTAL_PADDING.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
            ) {
                val halfSectorWidth = getX(APPARENT_RAY_LENGTH, 90 - HORIZONTAL_FOV / 2)
                repeat(NUM_HORIZONTAL_SCALE_LINES) { i ->
                    MeasureUnconstrainedViewWidth(viewToMeasure = {
                        Text(
                            text = "${((-halfSectorWidth) + (2 * halfSectorWidth / (NUM_HORIZONTAL_SCALE_LINES - 1) * i)).toInt()}m",
                            fontSize = RULER_FONT_SIZE.sp
                        )
                    }) { measuredWidth ->
                        Text(
                            text = "${((-halfSectorWidth) + (2 * halfSectorWidth / (NUM_HORIZONTAL_SCALE_LINES - 1) * i)).toInt()}m",
                            modifier = Modifier.offset(
                                (size.width / (NUM_HORIZONTAL_SCALE_LINES - 1) * i - measuredWidth.value / 2).dp,
                                0.dp
                            ),
                            fontSize = RULER_FONT_SIZE.sp
                        )
                    }
                }
            }
        }
    }
}
