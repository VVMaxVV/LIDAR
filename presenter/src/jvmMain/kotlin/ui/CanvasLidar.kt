package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
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
import androidx.compose.ui.focus.onFocusEvent
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
import util.consts.DefaultValues
import util.div
import util.getX
import util.measureViewHeight
import util.measureViewWidth
import util.times
import viewModel.CanvasLidarViewModel
import viewModel.ControllerMovementsViewModel
import viewModel.RayCalculationViewModel

private const val CANVAS_VERTICAL_PADDING = 12f
private const val CANVAS_HORIZONTAL_PADDING = 12f
private const val CANVAS_TOP_MARGIN = 16
private const val CANVAS_START_MARGIN = 64
private const val RULER_FONT_SIZE = 16
private const val ERROR_MESSAGE_MARGIN = 4
private val canvasSize = Size(400f, 420f)

internal class CanvasLidar(
    private val rayCalculationViewModel: RayCalculationViewModel,
    private val controllerMovementsViewModel: ControllerMovementsViewModel,
    private val canvasLidarViewModel: CanvasLidarViewModel
) {
    private var canvasViewSizeState by mutableStateOf<Size?>(null)

    private var rayConfiguration by mutableStateOf(rayCalculationViewModel.rayTracingConfiguration)
    private var apparentVisibility by mutableStateOf(rayCalculationViewModel.apparentVisibility)

    private lateinit var canvasReference: ConstrainedLayoutReference
    private lateinit var verticalCanvasRulerReference: ConstrainedLayoutReference
    private lateinit var horizontalCanvasRulerReference: ConstrainedLayoutReference
    private lateinit var errorMessageReference: ConstrainedLayoutReference

    @Preview
    @Composable
    fun display() {
        ConstraintLayout {
            initConstraintRefs()
            printCanvas()
            printVerticalCanvasRulerLabel()
            printHorizontalCanvasRulerLabel()
            handleErrorMessage()
        }
    }

    private fun ConstraintLayoutScope.initConstraintRefs() {
        canvasReference = createRef()
        verticalCanvasRulerReference = createRef()
        horizontalCanvasRulerReference = createRef()
        errorMessageReference = createRef()
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun ConstraintLayoutScope.printCanvas() {
        val requester = remember { FocusRequester() }
        LaunchedEffect(Unit) { requester.requestFocus() }
        canvasLidarViewModel.setCanvasFocus(requester)
        Canvas(
            Modifier.constrainAs(canvasReference) {
                top.linkTo(parent.top, margin = CANVAS_TOP_MARGIN.dp)
                start.linkTo(parent.start, margin = CANVAS_START_MARGIN.dp)
            }.size(canvasSize.width.dp, canvasSize.height.dp).background(Color.Black)
                .padding(vertical = CANVAS_VERTICAL_PADDING.dp, horizontal = CANVAS_HORIZONTAL_PADDING.dp)
                .onKeyEvent { handleKeyEvent(it) }
                .focusRequester(requester)
                .focusable()
                .onClick { requester.requestFocus() }
                .onFocusEvent { rayCalculationViewModel.fetchPointsInterception() }
                .onSizeChanged { canvasViewSizeState = it.toSize() }
                .graphicsLayer {
                    rotationY = 180f
                    rotationZ = 180f
                    canvasViewSizeState?.let { viewSize ->
                        apparentVisibility.value?.let { apparentVisibility ->
                            rayConfiguration.value?.horizontalFov?.let { horizontalFov ->
                                scaleY = viewSize.height.div(apparentVisibility)
                                translationY =
                                    -((viewSize.height) / 2 - apparentVisibility.toFloat() / 2).times(scaleY)
                                scaleX = (viewSize.width / 2).div(getX(apparentVisibility, 90 - horizontalFov / 2))
                                translationX = (viewSize.width / -2).times(scaleX)
                            }
                        }
                    }
                }
        ) {
            setupConfigurationViewModels()
            drawHorizontalRulerLines()
            drawVerticalRulerLines()
            printRays()
            printIntersectionsPoints()
        }
    }

    private fun DrawScope.drawHorizontalRulerLines() {
        apparentVisibility.value?.let { apparentRayLength ->
            rayConfiguration.value?.horizontalFov?.let { horizontalFov ->
                val halfSectorWidth = getX(apparentRayLength, 90 - horizontalFov / 2)
                val lineStartYCoordinate = -CANVAS_VERTICAL_PADDING * 2 * apparentRayLength / size.height
                val lineEndYCoordinate = -CANVAS_VERTICAL_PADDING * apparentRayLength / size.height
                drawIntoCanvas {
                    it.nativeCanvas.apply {
                        repeat(DefaultValues.NUM_HORIZONTAL_SCALE_LINES) { i ->
                            val lineXCoordinate =
                                (-halfSectorWidth).plus((halfSectorWidth * 2 / (DefaultValues.NUM_HORIZONTAL_SCALE_LINES - 1)) * i)
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
        }
    }

    private fun DrawScope.drawVerticalRulerLines() {
        apparentVisibility.value?.let { apparentRayLength ->
            rayConfiguration.value?.horizontalFov?.let { horizontalFov ->
                val halfSectorWidth = getX(apparentRayLength, 90 - horizontalFov / 2)
                val lineStartXCoordinate =
                    (-halfSectorWidth).minus((halfSectorWidth * 2 / size.width) * CANVAS_HORIZONTAL_PADDING * 2)
                val lineEndXCoordinate =
                    (-halfSectorWidth).minus((halfSectorWidth * 2 / size.width) * CANVAS_HORIZONTAL_PADDING)
                drawIntoCanvas {
                    it.nativeCanvas.apply {
                        repeat(DefaultValues.NUM_VERTICAL_SCALE_LINES) { i ->
                            drawIntoCanvas {
                                val lineYCoordinate = apparentRayLength / (DefaultValues.NUM_VERTICAL_SCALE_LINES - 1f) * i
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
                color = DefaultValues.raysColor
            )
        }
    }

    private fun DrawScope.printIntersectionsPoints() {
        rayCalculationViewModel.pointList.value.let { pointList ->
            drawPoints(
                points = pointList,
                pointMode = PointMode.Points,
                strokeWidth = DefaultValues.COLLISION_POINTS_SIZE,
                color = DefaultValues.collisionPointsColor
            )
        }
    }

    private fun setupConfigurationViewModels() {
        controllerMovementsViewModel.setCurrentPosition(DefaultValues.startPosition)
    }

    @Composable
    private fun ConstraintLayoutScope.printVerticalCanvasRulerLabel() {
        apparentVisibility.value?.let { apparentVisibility ->
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
                    repeat(DefaultValues.NUM_VERTICAL_SCALE_LINES) { i ->
                        measureViewHeight(viewToMeasure = {
                            Text(
                                text = "${(apparentVisibility / (DefaultValues.NUM_VERTICAL_SCALE_LINES - 1f) * (DefaultValues.NUM_VERTICAL_SCALE_LINES - i - 1)).toInt()}m",
                                fontSize = RULER_FONT_SIZE.sp
                            )
                        }) {
                            Text(
                                text = "${(apparentVisibility / (DefaultValues.NUM_VERTICAL_SCALE_LINES - 1f) * (DefaultValues.NUM_VERTICAL_SCALE_LINES - i - 1)).toInt()}m",
                                modifier = Modifier.offset(
                                    0.dp,
                                    (size.height / (DefaultValues.NUM_VERTICAL_SCALE_LINES - 1) * i - it.value / 2).dp
                                ),
                                fontSize = RULER_FONT_SIZE.sp
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ConstraintLayoutScope.printHorizontalCanvasRulerLabel() {
        apparentVisibility.value?.let { apparentVisibility ->
            canvasViewSizeState?.let { size ->
                rayConfiguration.value?.horizontalFov?.let { horizontalFov ->
                    Box(
                        Modifier.constrainAs(horizontalCanvasRulerReference) {
                            top.linkTo(canvasReference.bottom, margin = CANVAS_VERTICAL_PADDING.dp)
                            start.linkTo(canvasReference.start, margin = CANVAS_HORIZONTAL_PADDING.dp)
                            end.linkTo(canvasReference.end, margin = CANVAS_HORIZONTAL_PADDING.dp)
                            width = Dimension.fillToConstraints
                            height = Dimension.wrapContent
                        }
                    ) {
                        val halfSectorWidth = getX(apparentVisibility, 90 - horizontalFov / 2)
                        repeat(DefaultValues.NUM_HORIZONTAL_SCALE_LINES) { i ->
                            measureViewWidth(viewToMeasure = {
                                Text(
                                    text = "${((-halfSectorWidth) + (2 * halfSectorWidth / (DefaultValues.NUM_HORIZONTAL_SCALE_LINES - 1) * i)).toInt()}m",
                                    fontSize = RULER_FONT_SIZE.sp
                                )
                            }) { measuredWidth ->
                                Text(
                                    text = "${((-halfSectorWidth) + (2 * halfSectorWidth / (DefaultValues.NUM_HORIZONTAL_SCALE_LINES - 1) * i)).toInt()}m",
                                    modifier = Modifier.offset(
                                        (size.width / (DefaultValues.NUM_HORIZONTAL_SCALE_LINES - 1) * i - measuredWidth.value / 2).dp,
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
    }

    @Composable
    private fun ConstraintLayoutScope.handleErrorMessage() {
        rayCalculationViewModel.errorText.value?.let {
            Text(
                text = it,
                modifier = Modifier.constrainAs(errorMessageReference) {
                    top.linkTo(canvasReference.top, margin = ERROR_MESSAGE_MARGIN.dp)
                    end.linkTo(canvasReference.end, margin = ERROR_MESSAGE_MARGIN.dp)
                    bottom.linkTo(canvasReference.bottom, margin = ERROR_MESSAGE_MARGIN.dp)
                    start.linkTo(canvasReference.start, margin = ERROR_MESSAGE_MARGIN.dp)
                },
                color = Color.Red
            )
        }
    }
}
