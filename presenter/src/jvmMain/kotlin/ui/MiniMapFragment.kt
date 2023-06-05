package ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.onClick
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
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
        Box(Modifier.padding(16.dp)) {
            Canvas(
                Modifier.size(400.dp).background(Color.Black)
                    .focusRequester(requester)
                    .focusable()
                    .onClick { requester.requestFocus() }
                    .graphicsLayer {
                        translationX = 800f
                        translationY = 800f
                        scaleX = 4f
                        scaleY = 4f
                        rotationX = 180f
                    }
            ) {
                drawObstacles()
                drawCurrentPosition()
                drawGoalPoint()
                drawRays()
                drawTrajectory()
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
            ratListState.value.forEach {
                drawLine(Color.White, it.start, it.end, 0.15f)
            }
        }
        miniMapViewModel.fetchRays()
    }

    private fun DrawScope.drawTrajectory() {
        miniMapViewModel.trajectory.value.map {
            Offset(it.x.toFloat(), it.y.toFloat())
        }.also {
            drawPoints(it, PointMode.Polygon, Color.Blue, 1f)
        }
    }
}
