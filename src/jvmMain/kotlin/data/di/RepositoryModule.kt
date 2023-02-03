package data.di

import data.repository.ObstaclesRepositoryImpl
import domain.repository.ObstaclesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ObstaclesRepository> { ObstaclesRepositoryImpl(get()) }
}
