package di

import org.koin.dsl.module
import repository.LidarDataRepository
import repository.LidarDataRepositoryImpl
import repository.ObstaclesRepository
import repository.ObstaclesRepositoryImpl
import repository.UiRepository
import repository.UiRepositoryImpl

internal val repositoryModule = module {
    single<ObstaclesRepository> { ObstaclesRepositoryImpl(get()) }
    single<UiRepository> { UiRepositoryImpl(get()) }
    single<LidarDataRepository> { LidarDataRepositoryImpl(get(), get()) }
}
