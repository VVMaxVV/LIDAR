package domain.di

import domain.useCase.GetDistanceToCollisionUseCase
import domain.useCase.GetUiRaysUseCase
import domain.useCase.SetupLidarConfigurationUseCase
import domain.useCase.impl.GetDistanceToCollisionUseCaseImpl
import domain.useCase.impl.GetLidarUiRaysUseCaseImpl
import domain.useCase.impl.SetupLidarConfigurationUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<GetUiRaysUseCase> { GetLidarUiRaysUseCaseImpl(get()) }
    single<GetDistanceToCollisionUseCase> { GetDistanceToCollisionUseCaseImpl(get()) }
    single<SetupLidarConfigurationUseCase> { SetupLidarConfigurationUseCaseImpl(get()) }
}
