package di

import org.koin.dsl.module
import useCase.AddSeenPointUseCase
import useCase.GetCurrentPositionAsOffsetUseCase
import useCase.GetCurrentPositionUseCase
import useCase.GetDistanceToCollisionUseCase
import useCase.GetGoalPointUseCase
import useCase.GetNearestObstaclesUseCase
import useCase.GetObstaclesAroundUseCase
import useCase.GetPathUseCase
import useCase.GetPointInterceptionUseCase
import useCase.GetRaysOnPlaneUseCase
import useCase.GetTrajectoryUseCase
import useCase.GetUiRaysUseCase
import useCase.IsMovePossibleUseCase
import useCase.MoveToGoalUseCase
import useCase.MoveUseCase
import useCase.SetCurrentPositionUseCase
import useCase.SetGoalPointUseCase
import useCase.SetupRayTracingConfigurationUseCase
import useCase.impl.AddSeenPointUseCaseImpl
import useCase.impl.GetCurrentPositionAsOffsetUseCaseImpl
import useCase.impl.GetCurrentPositionUseCaseImpl
import useCase.impl.GetDistanceToCollisionUseCaseImpl
import useCase.impl.GetGoalPointUseCaseImpl
import useCase.impl.GetLidarUiRaysUseCaseImpl
import useCase.impl.GetNearestObstaclesUseCaseImpl
import useCase.impl.GetObstaclesAroundUseCaseImpl
import useCase.impl.GetPathUseCaseImpl
import useCase.impl.GetPointInterceptionUseCaseImpl
import useCase.impl.GetRaysOnPlaneUseCaseImpl
import useCase.impl.GetTrajectoryUseCaseImpl
import useCase.impl.IsMovePossibleUseCaseImpl
import useCase.impl.MoveToGoalUseCaseImpl
import useCase.impl.MoveUseCaseImpl
import useCase.impl.SetCurrentPositionUseCaseImpl
import useCase.impl.SetGoalPointUseCaseImpl
import useCase.impl.SetupRayTracingConfigurationUseCaseImpl

internal val useCaseModule = module {
    single<GetUiRaysUseCase> { GetLidarUiRaysUseCaseImpl(get()) }
    single<GetDistanceToCollisionUseCase> { GetDistanceToCollisionUseCaseImpl(get()) }
    single<SetupRayTracingConfigurationUseCase> { SetupRayTracingConfigurationUseCaseImpl(get()) }
    single<SetCurrentPositionUseCase> { SetCurrentPositionUseCaseImpl(get()) }
    single<GetNearestObstaclesUseCase> { GetNearestObstaclesUseCaseImpl(get()) }
    single<GetPathUseCase> { GetPathUseCaseImpl(get()) }
    single<GetPointInterceptionUseCase> { GetPointInterceptionUseCaseImpl(get()) }
    single<AddSeenPointUseCase> { AddSeenPointUseCaseImpl(get()) }
    single<GetObstaclesAroundUseCase> { GetObstaclesAroundUseCaseImpl(get(), get()) }
    single<GetCurrentPositionAsOffsetUseCase> { GetCurrentPositionAsOffsetUseCaseImpl(get()) }
    single<GetCurrentPositionUseCase> { GetCurrentPositionUseCaseImpl(get()) }
    single<SetGoalPointUseCase> { SetGoalPointUseCaseImpl(get()) }
    single<GetGoalPointUseCase> { GetGoalPointUseCaseImpl(get()) }
    single<IsMovePossibleUseCase> { IsMovePossibleUseCaseImpl(get(), get()) }
    single<MoveUseCase> { MoveUseCaseImpl(get(), get(), get()) }
    single<MoveToGoalUseCase> { MoveToGoalUseCaseImpl(get(), get(), get(), get(), get()) }
    single<GetRaysOnPlaneUseCase> { GetRaysOnPlaneUseCaseImpl(get()) }
    single<GetTrajectoryUseCase> { GetTrajectoryUseCaseImpl(get()) }
}
