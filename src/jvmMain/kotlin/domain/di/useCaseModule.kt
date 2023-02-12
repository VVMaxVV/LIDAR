package domain.di

import domain.useCase.GetDistanceToObstaclesUseCase
import domain.useCase.GetUiRaysUseCase
import domain.useCase.impl.GetDistanceToObstaclesUseCaseImpl
import domain.useCase.impl.GetLidarUiRaysUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    single<GetDistanceToObstaclesUseCase> { GetDistanceToObstaclesUseCaseImpl() }
    single<GetUiRaysUseCase> { GetLidarUiRaysUseCaseImpl(get()) }
}
