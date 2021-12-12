package r2.llc.referee.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import r2.llc.referee.listeners.BroadcastLiveData
import r2.llc.referee.listeners.ListenerUtil
import r2.llc.referee.listeners.NetworkLiveData
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    val networkLiveData: NetworkLiveData,
    val broadcastLiveData: BroadcastLiveData,

    private val listenerUtil: ListenerUtil
) : ViewModel() {

    suspend fun loadData() {
        delay(1000)
    }

    val networkFlow: Flow<Boolean> =
        listenerUtil
            .networkFlow()
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
}