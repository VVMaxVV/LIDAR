package data.di

import data.factory.ObstaclesFactory
import org.koin.dsl.module

val factoryModule = module {
    single { ObstaclesFactory() }
}
