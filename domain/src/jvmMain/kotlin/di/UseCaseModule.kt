package di

import org.koin.dsl.module
import useCase.AddSeenPointUseCase
import useCase.GetDistanceToCollisionUseCase
import useCase.GetNearestObstaclesUseCase
import useCase.GetPathUseCase
import useCase.GetPointInterceptionUseCase
import useCase.GetUiRaysUseCase
import useCase.SetCurrentPositionUseCase
import useCase.SetupRayTracingConfigurationUseCase
import useCase.impl.AddSeenPointUseCaseImpl
import useCase.impl.GetDistanceToCollisionUseCaseImpl
import useCase.impl.GetLidarUiRaysUseCaseImpl
import useCase.impl.GetNearestObstaclesUseCaseImpl
import useCase.impl.GetPathUseCaseImpl
import useCase.impl.GetPointInterceptionUseCaseImpl
import useCase.impl.SetCurrentPositionUseCaseImpl
import useCase.impl.SetupRayTracingConfigurationUseCaseImpl

internal val useCaseModule = module {
    single<GetUiRaysUseCase> { GetLidarUiRaysUseCaseImpl(get()) }
    single<GetDistanceToCollisionUseCase> { GetDistanceToCollisionUseCaseImpl(get()) }
    single<SetupRayTracingConfigurationUseCase> { SetupRayTracingConfigurationUseCaseImpl(get()) }
    single<SetCurrentPositionUseCase> { SetCurrentPositionUseCaseImpl(get()) }
    single<GetNearestObstaclesUseCase> { GetNearestObstaclesUseCaseImpl(get()) }
    single<GetPathUseCase> { GetPathUseCaseImpl(get()) }
    single<GetPointInterceptionUseCase> { GetPointInterceptionUseCaseImpl(get()) }
    single<AddSeenPointUseCase> { AddSeenPointUseCaseImpl(get()) }
}
