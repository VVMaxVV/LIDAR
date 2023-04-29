package useCase.impl

import model.Point
import repository.LidarDataRepository
import useCase.GetPointInterceptionUseCase

internal class GetPointInterceptionUseCaseImpl(private val lidarDataRepository: LidarDataRepository) :
    GetPointInterceptionUseCase {
    override fun execute(): List<Point> {
        return lidarDataRepository.getPointsInterception()
    }
}
