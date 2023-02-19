package presenter.di

import org.koin.dsl.module
import presenter.mapper.DistanceToCollisionMapper
import presenter.mapper.PointMapper
import presenter.mapper.RayMapper

val mapperModule = module {
    single { RayMapper() }
    single { PointMapper() }
    single { DistanceToCollisionMapper(get()) }
}
