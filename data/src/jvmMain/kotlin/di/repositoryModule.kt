package di

import org.koin.dsl.module
import repository.*

internal val repositoryModule = module {
    single<ObstaclesRepository> { ObstaclesRepositoryImpl(get()) }
    single<UiRepository> { UiRepositoryImpl(get()) }
    single<LidarDataRepository> { LidarDataRepositoryImpl(get(), get()) }
}
