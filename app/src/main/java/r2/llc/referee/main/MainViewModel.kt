package r2.llc.referee.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    private val _scoreFlow: MutableStateFlow<MainModel> = MutableStateFlow(MainModel())
    val scoreFlow: StateFlow<MainModel> get() = _scoreFlow.asStateFlow()

    private val _timerFlow: MutableStateFlow<Long> = MutableStateFlow(0)
    val timerFlow: StateFlow<Long> get() = _timerFlow.asStateFlow()

    private val _isStartFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isStartFlow: StateFlow<Boolean> get() = _isStartFlow.asStateFlow()

    private val _resultFlow: MutableSharedFlow<MainModel> = MutableSharedFlow()
    val resultFlow: SharedFlow<MainModel> get() = _resultFlow.asSharedFlow()

    fun onGoalTop() {
        val lastScore = scoreFlow.value
        _scoreFlow.value = lastScore.copy(topScore = lastScore.topScore + 1)
    }

    fun onGoalBottom() {
        val lastScore = scoreFlow.value
        _scoreFlow.value = lastScore.copy(bottomScore = lastScore.bottomScore + 1)
    }

    fun onStart() {
        var time = 0L
        _isStartFlow.value = true
        viewModelScope.launch(Dispatchers.IO) {
            while (++time < 60L) {
                delay(1000L)
                _timerFlow.value = time
            }

            _resultFlow.emit(scoreFlow.value)
        }
    }

    fun onReset() {
        _scoreFlow.value = MainModel()
        _isStartFlow.value = false
    }
}