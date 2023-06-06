package model.sealedClass

sealed class DistanceToCollision {
    object OutOfBound : DistanceToCollision()
    data class WithinMeasurement(val distance: Number) : DistanceToCollision()
}
