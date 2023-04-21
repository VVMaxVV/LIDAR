package di

import mapper.OffsetMapper
import mapper.PointMapper
import org.koin.dsl.module

internal val mapperDataModule = module {
    single { OffsetMapper() }
    single { PointMapper() }
}
