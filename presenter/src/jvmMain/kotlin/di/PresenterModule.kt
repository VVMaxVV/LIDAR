package di

import org.koin.dsl.module

val presenterModule = module {
    includes(
        mapperModule,
        uiModule,
        viewModelModule
    )
}
