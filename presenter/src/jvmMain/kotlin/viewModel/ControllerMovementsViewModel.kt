package viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import exception.GoalPointNotDefinedException
import exception.PathNotExistException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import model.Movements
import useCase.MoveToGoalUseCase
import useCase.MoveUseCase

internal class ControllerMovementsViewModel(
    private val moveUseCase: MoveUseCase,
    private val moveToGoalUseCase: MoveToGoalUseCase
) {
    private var moveToGoalJob: Job? = null

    private val _movementTowardsGoalState = mutableStateOf<MovementTowardsGoal>(MovementTowardsGoal.Stopped)
    val movementTowardsGoalState: State<MovementTowardsGoal> get() = _movementTowardsGoalState

    private val _event = mutableStateOf<Event?>(null)
    val event: State<Event?> get() = _event

    fun buttonControlMovement() {
        if (_movementTowardsGoalState.value !is MovementTowardsGoal.Continues) {
            moveToGoalJob = CoroutineScope(Dispatchers.Default).launch {
                _movementTowardsGoalState.value = MovementTowardsGoal.Continues
                try {
                    moveToGoalUseCase.execute()
                    _movementTowardsGoalState.value = MovementTowardsGoal.Stopped
                    _event.value = Event.DestinationReached
                } catch (e: PathNotExistException) {
                    _event.value = Event.PathNotExist
                    _movementTowardsGoalState.value = MovementTowardsGoal.NotPossible
                } catch (e: GoalPointNotDefinedException) {
                    _event.value = Event.GoalNotDefined
                    _movementTowardsGoalState.value = MovementTowardsGoal.Stopped
                }
            }
        } else {
            moveToGoalJob?.cancel()
            _movementTowardsGoalState.value = MovementTowardsGoal.Stopped
        }
    }

    fun move(movements: Movements) {
        CoroutineScope(Dispatchers.Default).launch {
            moveUseCase.execute(movements)
        }
    }

    sealed class MovementTowardsGoal {
        abstract fun getTextContent(): String
        object Continues : MovementTowardsGoal() {
            override fun getTextContent(): String = "Stop move"
        }
        object Stopped : MovementTowardsGoal() {
            override fun getTextContent(): String = "Start move"
        }
        object NotPossible : MovementTowardsGoal() {
            override fun getTextContent(): String = "Move is not possible"
        }
    }

    sealed class Event {
        object GoalNotDefined : Event()
        object PathNotExist : Event()
        object DestinationReached : Event()
    }
}
