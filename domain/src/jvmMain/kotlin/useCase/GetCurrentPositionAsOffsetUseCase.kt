package useCase

import androidx.compose.ui.geometry.Offset

interface GetCurrentPositionAsOffsetUseCase {
    fun execute(): Offset?
}
