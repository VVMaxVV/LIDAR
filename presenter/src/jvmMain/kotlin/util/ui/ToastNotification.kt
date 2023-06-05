package util.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import viewModel.ToastViewModel

internal class ToastNotification(private val toastViewModel: ToastViewModel) {

    @Composable
    fun showToast() {
        var isViewVisible by remember { mutableStateOf(false) }
        val content by remember { toastViewModel.toastContentState }
        LaunchedEffect(content) {
            content?.let {
                isViewVisible = true
                delay(it.duration.getDuration())
                isViewVisible = false
            }
        }
        content?.let {
            if (isViewVisible) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .height(48.dp)
                        .background(Color(0xFFD7D7D7), RoundedCornerShape(50))
                ) {
                    Text(
                        text = it.message,
                        color = Color.Black,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    sealed class Duration {
        abstract fun getDuration(): kotlin.Long

        object Short : Duration() {
            override fun getDuration(): kotlin.Long = 2000
        }

        object Long : Duration() {
            override fun getDuration(): kotlin.Long = 5000
        }
    }
}
