package presenter.di

import org.koin.dsl.module
import presenter.mapper.RayMapper

val mapperModule = module {
    single { RayMapper() }
}
