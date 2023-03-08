package di

import mapper.DistanceToCollisionMapper
import mapper.PointMapper
import mapper.RayMapper
import org.koin.dsl.module

val mapperModule = module {
    single { RayMapper() }
    single { PointMapper() }
    single { DistanceToCollisionMapper(get()) }
}
