package presenter.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import domain.model.Point
import domain.model.Position
import domain.model.TiltAngle
import presenter.viewModel.RayCalculationViewModel

private const val RAYS_NUMBER = 16
private const val RAYS_HORIZONTAL_FOV = 48
private const val MAX_RAY_LENGTH = 45
private const val VISIBILITY_HEIGHT = 45
private const val VISIBILITY_WIDTH = 20
private const val COLLISION_POINTS_WIDTH = 3f
private val currentPosition = Position(Point(x = 0f, y = -10f), TiltAngle(20))
private val raysColor = Color.White
private val collisionPointsColor = Color.Green

internal class CanvasLidar(private val rayCalculationViewModel: RayCalculationViewModel) {

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
        Canvas(
            Modifier.constrainAs(canvasReference) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start, margin = 40.dp)
            }
                .size(400.dp, 420.dp)
                .background(Color.Black)
                .padding(vertical = 12.dp)
        ) {
            rayCalculationViewModel.setupLidarConfiguration(
                RAYS_NUMBER, RAYS_HORIZONTAL_FOV, MAX_RAY_LENGTH, currentPosition
            )
            printRays()
            printScaleLine()
            printPoints()
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

    private fun DrawScope.printRays() {
        rayCalculationViewModel.getRays(
            RAYS_NUMBER,
            RAYS_HORIZONTAL_FOV,
            MAX_RAY_LENGTH,
            size,
            VISIBILITY_HEIGHT,
            VISIBILITY_WIDTH
        )
        rayCalculationViewModel.rayList.value.map {
            drawLine(
                start = it.start,
                end = it.end,
                color = raysColor,
            )
        }
    }

    private fun DrawScope.printPoints() {
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
                color = Color.Black,
            )
        }
    }

    @Composable
    private fun printLabelsScale() {
        Text(
            text = "45\n40\n35\n30\n25\n20\n15\n10\n 5\n 0",
            Modifier.padding(end = 10.dp),
            lineHeight = 44.sp,
        )
    }
}
