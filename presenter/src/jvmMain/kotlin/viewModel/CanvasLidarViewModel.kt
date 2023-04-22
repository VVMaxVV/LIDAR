package viewModel

import androidx.compose.ui.focus.FocusRequester

internal class CanvasLidarViewModel {
    private var focus: FocusRequester? = null

    fun setCanvasFocus(focus: FocusRequester) {
        this.focus = focus
    }

    fun focusableOnCanvas() {
        focus?.requestFocus()
    }
}
