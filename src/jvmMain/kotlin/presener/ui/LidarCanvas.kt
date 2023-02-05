package presener.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope

const val RAYS_NUMBER = 16

internal class LidarCanvas {

    @Preview
    @Composable
    fun display() {
        ConstraintLayout {
            val (canvasReference) = createRefs()
            printCanvas(canvasReference)
        }
    }

    @Preview
    @Composable
    private fun ConstraintLayoutScope.printCanvas(canvasReference: ConstrainedLayoutReference) {
        Canvas(
            Modifier.constrainAs(canvasReference) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
            }
                .size(400.dp, 400.dp)
                .background(Color.Black)
        ) {
            printRays()
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
}
