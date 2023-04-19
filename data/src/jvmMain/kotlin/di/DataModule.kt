package di

import org.koin.dsl.module

val dataModule = module {
    includes(
        factoryModule,
        repositoryModule,
        mapperDataModule
    )
}
