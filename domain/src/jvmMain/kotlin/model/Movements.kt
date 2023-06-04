package model

sealed class Movements {
    object MoveLeft : Movements()
    object MoveRight : Movements()
    object MoveForward : Movements()
    object MoveBackward : Movements()
    object RotateClockwise : Movements()
    object RotateCounterclockwise : Movements()
}
