package di

import org.koin.dsl.module
import useCase.GetDistanceToCollisionUseCase
import useCase.GetUiRaysUseCase
import useCase.SetCurrentPositionUseCase
import useCase.SetupRayTracingConfigurationUseCase
import useCase.impl.GetDistanceToCollisionUseCaseImpl
import useCase.impl.GetLidarUiRaysUseCaseImpl
import useCase.impl.SetCurrentPositionUseCaseImpl
import useCase.impl.SetupRayTracingConfigurationUseCaseImpl

internal val useCaseModule = module {
    single<GetUiRaysUseCase> { GetLidarUiRaysUseCaseImpl(get()) }
    single<GetDistanceToCollisionUseCase> { GetDistanceToCollisionUseCaseImpl(get()) }
    single<SetupRayTracingConfigurationUseCase> { SetupRayTracingConfigurationUseCaseImpl(get()) }
    single<SetCurrentPositionUseCase> { SetCurrentPositionUseCaseImpl(get()) }
}
