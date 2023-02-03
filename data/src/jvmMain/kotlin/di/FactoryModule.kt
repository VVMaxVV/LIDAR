package di

import factory.ObstaclesFactory
import factory.PathFactory
import factory.RaysFactory
import org.koin.dsl.module

internal val factoryModule = module {
    single { ObstaclesFactory() }
    single { RaysFactory() }
    single { PathFactory() }
}
