package useCase.impl

import androidx.compose.ui.geometry.Offset
import repository.LidarDataRepository
import useCase.GetCurrentPositionAsOffsetUseCase

internal class GetCurrentPositionAsOffsetUseCaseImpl(
    private val lidarDataRepository: LidarDataRepository
) :
    GetCurrentPositionAsOffsetUseCase {
    override fun execute(): Offset? {
        return lidarDataRepository.getCurrentPosition()?.currentCoordinates?.let {
            Offset(it.x.toFloat(),it.y.toFloat())
        }
    }
}