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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import viewModel.MiniMapViewModel
import viewModel.RefreshContentCanvasViewModel

private const val CANVAS_VERTICAL_PADDING = 12f

internal class MiniMapFragment(
    private val miniMapViewModel: MiniMapViewModel,
    private val refreshContentCanvasViewModel: RefreshContentCanvasViewModel
) {
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun display() {
        val requester = remember { FocusRequester() }
        refreshContentCanvasViewModel.addFocus(requester)
        LaunchedEffect(Unit) { requester.requestFocus() }
        Box(Modifier.padding(16.dp)) {
            Canvas(Modifier.size(400.dp).background(Color.Black)
                .focusRequester(requester)
                .focusable()
                .onClick { requester.requestFocus() }
                .graphicsLayer {
                    translationX = 800f
                    translationY = 800f
                    scaleX = 4f
                    scaleY = 4f
                    rotationX = 180f
                }.onKeyEvent {
                    miniMapViewModel.fetchCurrentPosition()
                    return@onKeyEvent false
                }
            ) {
                drawObstacles()
                drawCurrentPosition()
            }
        }
    }

    private fun DrawScope.drawObstacles() {
        miniMapViewModel.fetchObstacles()
        val obstacleList = miniMapViewModel.obstaclesList.value
        obstacleList.forEach {
            drawLine(color = Color.White, it.start, it.end, 1f)
        }
    }

    private fun DrawScope.drawCurrentPosition() {
        miniMapViewModel.fetchCurrentPosition()
        miniMapViewModel.currentPosition.value?.let {
            drawPoints(listOf(it), PointMode.Points, Color.Green, 1f)
        }
    }
}