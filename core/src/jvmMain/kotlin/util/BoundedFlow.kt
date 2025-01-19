package util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class BoundedFlow<T>(private val capacity: Int) {
    private val _state = MutableStateFlow<List<T>>(emptyList())
    val state: StateFlow<List<T>> = _state

    private val mutex = Mutex()

    suspend fun add(element: T) {
        mutex.withLock {
            val updatedList = _state.value.toMutableList()
            updatedList.add(element)
            if (updatedList.size > capacity) {
                updatedList.removeFirst()
            }
            _state.value = updatedList
        }
    }

    suspend fun clear() {
        mutex.withLock {
            _state.value = emptyList()
        }
    }
}
