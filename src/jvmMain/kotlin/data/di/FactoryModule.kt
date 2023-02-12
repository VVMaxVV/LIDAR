package data.di

import data.factory.ObstaclesFactory
import data.factory.UiRaysFactory
import org.koin.dsl.module

val factoryModule = module {
    single { ObstaclesFactory() }
    single { UiRaysFactory() }
}
