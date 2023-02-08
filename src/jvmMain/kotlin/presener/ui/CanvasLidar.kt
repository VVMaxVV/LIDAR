package presener.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope

const val RAYS_NUMBER = 16

internal class CanvasLidar {

    @Preview
    @Composable
    fun display() {
        ConstraintLayout {
            val (canvasReference, scaleColumReference) = createRefs()
            printCanvas(canvasReference, scaleColumReference)
        }
    }

    @Composable
    private fun ConstraintLayoutScope.printCanvas(
        canvasReference: ConstrainedLayoutReference,
        scaleColumReference: ConstrainedLayoutReference
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
            printRays()
            printScaleLine()
        }
        Column(
            Modifier.constrainAs(scaleColumReference) {
                top.linkTo(canvasReference.top, margin = 12.dp)
                bottom.linkTo(canvasReference.bottom, margin = 32.dp)
                end.linkTo(canvasReference.start)
            }
//                .background(Color.Red),
        ) {
            printScaleValue()
        }
    }

    private fun DrawScope.printRays() {
        for (i in 0 until RAYS_NUMBER) {
            drawLine(
                start = Offset(x = size.width / (RAYS_NUMBER - 1) * i, y = 0f),
                end = Offset(x = size.width / 2, y = size.height),
                color = Color.White
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
    private fun ColumnScope.printScaleValue() {
        Text(
            text = "45\n40\n35\n30\n25\n20\n15\n10\n 5\n 0",
            Modifier.padding(end = 10.dp),
            lineHeight = 44.sp,
        )
    }

}
