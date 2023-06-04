package viewModel

import androidx.compose.ui.focus.FocusRequester

internal class RefreshContentCanvasViewModel {
    private val focusList = mutableListOf<FocusRequester>()

    fun addFocus(focus: FocusRequester) {
        focusList.add(focus)
    }

    fun refreshContent(currentFocus: FocusRequester? = null) {
        focusList.forEach {
            it.requestFocus()
        }
        currentFocus?.requestFocus()
    }
}
