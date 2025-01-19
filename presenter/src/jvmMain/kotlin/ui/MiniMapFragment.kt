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
import viewModel.MiniMapViewModel
import viewModel.NavigationViewModel
import viewModel.RefreshContentCanvasViewModel

internal class MiniMapFragment(
    private val miniMapViewModel: MiniMapViewModel,
    private val refreshContentCanvasViewModel: RefreshContentCanvasViewModel,
    private val navigationViewModel: NavigationViewModel
) {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun display() {
        val requester = remember { FocusRequester() }
        refreshContentCanvasViewModel.addFocus(requester)
        LaunchedEffect(Unit) { requester.requestFocus() }
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
                    drawObstacles()
                    drawRays()
                    drawTrajectory()
                    drawGoalPoint()
                    drawCurrentPosition()
                    drawPlannedTrajectory()
                }
            }
        }
    }

    private fun DrawScope.drawObstacles() {
        miniMapViewModel.fetchObstacles()
        miniMapViewModel.obstaclesList.value.forEach {
            drawLine(color = Color.White, it.start, it.end, 1f)
        }
    }

    private fun DrawScope.drawCurrentPosition() {
        navigationViewModel.currentPosition.value?.currentCoordinates?.let {
            drawPoints(listOf(Offset(it.x.toFloat(), it.y.toFloat())), PointMode.Points, Color.Green, 1f)
        }
    }

    private fun DrawScope.drawGoalPoint() {
        navigationViewModel.goalPoint.value?.let {
            drawPoints(listOf(Offset(it.x.toFloat(), it.y.toFloat())), PointMode.Points, Color.Red, 1f)
        }
    }

    private fun DrawScope.drawRays() {
        miniMapViewModel.apply {
            fetchRays()
            rayListState.value.forEach {
                drawLine(Color.White, it.start, it.end, 0.15f)
                println("Ray: start ${it.start}, end: ${it.end}")
            }
        }
        miniMapViewModel.fetchRays()
    }

    private fun DrawScope.drawTrajectory() {
        if (miniMapViewModel.isTrajectoryVisible.value) {
            miniMapViewModel.trajectory.value.map {
                Offset(it.x.toFloat(), it.y.toFloat())
            }.also {
                drawPoints(it, PointMode.Polygon, Color.Blue, 1f)
            }
        }
    }

    private fun DrawScope.drawPlannedTrajectory() {
        if (miniMapViewModel.isPlannedTrajectoryVisible.value) {
            miniMapViewModel.plannedTrajectory.value.map {
                Offset(it.x.toFloat(), it.y.toFloat())
            }.also {
                drawPoints(it, PointMode.Polygon, Color.Yellow, 1f)
            }
        }
    }

    companion object {
        const val SCALE_FACTOR = 2F
    }
}
