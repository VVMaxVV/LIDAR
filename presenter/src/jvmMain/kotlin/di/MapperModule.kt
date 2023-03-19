package di

import mapper.DistanceToCollisionMapper
import org.koin.dsl.module

internal val mapperModule = module {
    single { DistanceToCollisionMapper() }
}
