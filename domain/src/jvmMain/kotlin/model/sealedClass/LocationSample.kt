package model.sealedClass

sealed class LocationSample {
    abstract fun getIndex(): Int
    object FirstSample : LocationSample() {
        override fun getIndex(): Int = 0
    }
    object SecondSample : LocationSample() {
        override fun getIndex(): Int = 1
    }
    object ThirdSample : LocationSample() {
        override fun getIndex(): Int = 2
    }
}
