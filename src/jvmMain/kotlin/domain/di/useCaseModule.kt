package domain.di

import domain.useCase.GetDistanceToCollisionUseCase
import domain.useCase.GetUiRaysUseCase
import domain.useCase.SetCurrentPositionUseCase
import domain.useCase.SetupRayTracingConfigurationUseCase
import domain.useCase.impl.GetDistanceToCollisionUseCaseImpl
import domain.useCase.impl.GetLidarUiRaysUseCaseImpl
import domain.useCase.impl.SetCurrentPositionUseCaseImpl
import domain.useCase.impl.SetupRayTracingConfigurationUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<GetUiRaysUseCase> { GetLidarUiRaysUseCaseImpl(get()) }
    single<GetDistanceToCollisionUseCase> { GetDistanceToCollisionUseCaseImpl(get()) }
    single<SetupRayTracingConfigurationUseCase> { SetupRayTracingConfigurationUseCaseImpl(get()) }
    single<SetCurrentPositionUseCase> { SetCurrentPositionUseCaseImpl(get()) }
}
