package di

import factory.ObstaclesFactory
import factory.RaysFactory
import org.koin.dsl.module

val factoryModule = module {
    single { ObstaclesFactory() }
    single { RaysFactory() }
}
