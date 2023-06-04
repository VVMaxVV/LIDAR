package repository

import androidx.compose.runtime.State
import model.Position

interface CurrentPositionRepository {
    suspend fun setCurrentPosition(position: Position)
    fun getCurrentPosition(): State<Position?>
}
