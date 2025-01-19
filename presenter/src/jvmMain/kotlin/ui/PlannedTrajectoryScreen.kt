package ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import org.koin.java.KoinJavaComponent.get
import ui.MiniMapFragment.Companion.SCALE_FACTOR
import viewModel.MiniMapViewModel
import viewModel.NavigationViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlannedTrajectoryScreen() {
//    Text("Planned Trajectory Screen")
    val requester = remember { FocusRequester() }
//    refreshContentCanvasViewModel.addFocus(requester)
    LaunchedEffect(Unit) { requester.requestFocus() }
//    val a = get<MiniMapViewModel>(MiniMapViewModel::class.java)
    val miniMapViewModel = get<MiniMapViewModel>(MiniMapViewModel::class.java)
    val navigationViewModel = get<NavigationViewModel>(NavigationViewModel::class.java)
    Box(
        Modifier.fillMaxWidth().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            Modifier.size(400.dp).background(Color.Black)
                .focusRequester(requester)
                .focusable()
                .onClick { requester.requestFocus() }
                .graphicsLayer {
                    translationX = SCALE_FACTOR * 200f
                    translationY = SCALE_FACTOR * 200f
                    scaleX = SCALE_FACTOR
                    scaleY = SCALE_FACTOR
                    rotationX = 180f
                }
        ) {
            clipRect(-200 / SCALE_FACTOR, -200 / SCALE_FACTOR, 200 / SCALE_FACTOR, 200 / SCALE_FACTOR) {
                drawObstacles(miniMapViewModel)
                drawRays(miniMapViewModel)
                drawTrajectory(miniMapViewModel)
                drawGoalPoint(navigationViewModel)
                drawCurrentPosition(navigationViewModel)
            }
        }
    }
}

private fun DrawScope.drawObstacles(miniMapViewModel: MiniMapViewModel) {
    miniMapViewModel.fetchObstacles()
    miniMapViewModel.obstaclesList.value.forEach {
        drawLine(color = Color.White, it.start, it.end, 1f)
    }
}

private fun DrawScope.drawCurrentPosition(navigationViewModel: NavigationViewModel) {
    navigationViewModel.currentPosition.value?.currentCoordinates?.let {
        drawPoints(listOf(Offset(it.x.toFloat(), it.y.toFloat())), PointMode.Points, Color.Green, 1f)
    }
}

private fun DrawScope.drawGoalPoint(navigationViewModel: NavigationViewModel) {
    navigationViewModel.goalPoint.value?.let {
        drawPoints(listOf(Offset(it.x.toFloat(), it.y.toFloat())), PointMode.Points, Color.Red, 1f)
    }
}

private fun DrawScope.drawRays(miniMapViewModel: MiniMapViewModel) {
    miniMapViewModel.apply {
        fetchRays()
        rayListState.value.forEach {
            drawLine(Color.White, it.start, it.end, 0.15f)
            println("Ray: start ${it.start}, end: ${it.end}")
        }
    }
    miniMapViewModel.fetchRays()
}

private fun DrawScope.drawTrajectory(miniMapViewModel: MiniMapViewModel) {
    if (miniMapViewModel.isTrajectoryVisible.value) {
        miniMapViewModel.trajectory.value.map {
            Offset(it.x.toFloat(), it.y.toFloat())
        }.also {
            drawPoints(it, PointMode.Polygon, Color.Blue, 1f)
        }
    }
}
