package data.di

import data.repository.ObstaclesRepositoryImpl
import data.repository.UiRepositoryImpl
import domain.repository.ObstaclesRepository
import domain.repository.UiRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ObstaclesRepository> { ObstaclesRepositoryImpl(get()) }
    single<UiRepository> { UiRepositoryImpl(get()) }
}
