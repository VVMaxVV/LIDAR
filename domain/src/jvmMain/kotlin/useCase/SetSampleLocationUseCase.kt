package useCase

import model.sealedClass.LocationSample

interface SetSampleLocationUseCase {
    suspend fun execute(locationSample: LocationSample)
}
