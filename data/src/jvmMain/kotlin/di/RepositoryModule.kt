package di

import org.koin.dsl.module
import repository.CurrentPositionRepository
import repository.GoalPointRepository
import repository.LidarDataRepository
import repository.LidarDataRepositoryImpl
import repository.ObstaclesRepository
import repository.ObstaclesRepositoryImpl
import repository.PathRepository
import repository.PathRepositoryImpl
import repository.TrajectoryRepository
import repository.TrajectoryRepositoryImpl
import repository.UiRepository
import repository.UiRepositoryImpl

internal val repositoryModule = module {
    single<ObstaclesRepository> { ObstaclesRepositoryImpl(get()) }
    single<UiRepository> { UiRepositoryImpl(get()) }
    single<LidarDataRepository> { LidarDataRepositoryImpl(get(), get(), get(), get()) }
    single<PathRepository> { PathRepositoryImpl(get()) }
    single<CurrentPositionRepository> { get<LidarDataRepository>() as LidarDataRepositoryImpl }
    single<GoalPointRepository> { get<PathRepository>() as PathRepositoryImpl }
    single<TrajectoryRepository> { TrajectoryRepositoryImpl() }
}
