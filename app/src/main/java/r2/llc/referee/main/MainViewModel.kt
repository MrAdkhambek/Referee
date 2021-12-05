package r2.llc.referee.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import r2.llc.referee.repository.MainRepository
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _state: MutableStateFlow<ResultState<Model>> = MutableStateFlow(ResultState.Loading)
    val state: StateFlow<ResultState<Model>> get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = ResultState.Loading
            try {
                val model = mainRepository.loadItems()
                _state.value = ResultState.Resource(model)
            } catch (t: Throwable) {
                _state.value =  ResultState.Error(t)
            }
        }
    }
}

sealed class ResultState<out T> {
    object Loading : ResultState<Nothing>()
    data class Resource(val model: Model) : ResultState<Model>()
    data class Error(val error: Throwable) : ResultState<Nothing>()
}

data class Model(
    val list: List<String>
)