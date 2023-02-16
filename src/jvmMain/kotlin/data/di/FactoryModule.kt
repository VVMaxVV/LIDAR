package data.di

import data.factory.ObstaclesFactory
import data.factory.RaysFactory
import org.koin.dsl.module

val factoryModule = module {
    single { ObstaclesFactory() }
    single { RaysFactory() }
}
