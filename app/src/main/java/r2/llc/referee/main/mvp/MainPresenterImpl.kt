package r2.llc.referee.main.mvp

import android.os.CountDownTimer
import java.lang.ref.WeakReference

class MainPresenterImpl : MainContract.Presenter {

    private var model: MainModelImpl = MainModelImpl()
    private var viewRef: WeakReference<MainContract.View>? = null

    override fun onGoalTop() {
        model = model.copy(topScore = model.topScore + 1)
        viewRef?.get()?.updateScore(model)
    }

    override fun onGoalBottom() {
        model = model.copy(bottomScore = model.bottomScore + 1)
        viewRef?.get()?.updateScore(model)
    }

    override fun onStart() {
        val time = 60 * 1000L
        model = model.copy(isStarted = true)

        viewRef?.get()?.onChangeGameStatus(model)
        object : CountDownTimer(time, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                viewRef?.get()?.updateTimer((time - millisUntilFinished) / 1000)
            }

            override fun onFinish() {
                viewRef?.get()?.showResult(model)
            }
        }.start()
    }

    override fun onReset() {
        model = MainModelImpl()
        viewRef?.get()?.updateScore(model)
        viewRef?.get()?.onChangeGameStatus(model)
    }

    override fun attachView(view: MainContract.View) {
        viewRef = WeakReference(view)
    }

    override fun detachView() {
        viewRef = null
    }
}