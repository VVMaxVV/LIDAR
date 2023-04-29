package viewModel

import model.Point
import model.Position
import useCase.GetPathUseCase

internal class NavigationViewModel(
    private val getPathUseCase: GetPathUseCase
) {
    fun getPath(currentPosition: Position, goal: Point): List<Point> =
        getPathUseCase.execute(currentPosition.currentCoordinates, goal)
}
