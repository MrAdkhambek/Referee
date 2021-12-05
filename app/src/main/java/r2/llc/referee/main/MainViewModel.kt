package r2.llc.referee.main

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import r2.llc.referee.base.SingleLiveData
import r2.llc.referee.main.mvp.MainContract
import r2.llc.referee.main.mvp.MainModelImpl


class MainViewModel : ViewModel() {

    private var model: MainModelImpl = MainModelImpl()

    private val _scoreLiveData: MutableLiveData<MainContract.Model> = MutableLiveData(model)
    val scoreLiveData: LiveData<MainContract.Model> get() = _scoreLiveData

    private val _timerLiveData: MutableLiveData<Long> = MutableLiveData(0)
    val timerLiveData: LiveData<Long> get() = _timerLiveData

    private val _isStartLiveData: MutableLiveData<Boolean> = MutableLiveData(false)
    val isStartLiveData: LiveData<Boolean> get() = _isStartLiveData

    private val _resultLiveData: SingleLiveData<MainContract.Model> = SingleLiveData()
    val resultLiveData: LiveData<MainContract.Model> get() = _resultLiveData

    fun onGoalTop() {
        model = model.copy(topScore = model.topScore + 1)
        _scoreLiveData.value = model
    }

    fun onGoalBottom() {
        model = model.copy(bottomScore = model.bottomScore + 1)
        _scoreLiveData.value = model
    }

    fun onStart() {
        val time = 60 * 1000L
        _isStartLiveData.value = true

        object : CountDownTimer(time, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                _timerLiveData.value = (time - millisUntilFinished) / 1000
            }

            override fun onFinish() {
                _resultLiveData.value = model
            }
        }.start()
    }

    fun onReset() {
        model = MainModelImpl()
        _scoreLiveData.value = model
        _isStartLiveData.value = false
    }
}