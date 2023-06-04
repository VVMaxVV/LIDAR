package viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import util.model.ToastContent
import util.ui.ToastNotification

internal class ToastViewModel {
    private val _toastContentState = mutableStateOf<ToastContent?>(null)
    val toastContentState: State<ToastContent?> get() = _toastContentState
    fun showToast(message: String, duration: ToastNotification.Duration = ToastNotification.Duration.Short) {
        _toastContentState.value = ToastContent(message, duration)
    }
}
