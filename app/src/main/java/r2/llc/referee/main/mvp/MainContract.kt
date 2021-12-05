package r2.llc.referee.main.mvp

import r2.llc.referee.base.BasePresenter

interface MainContract {

    interface View {

        fun updateScore(model: Model)
        fun updateTimer(time: Long)

        fun showResult(model: Model)
        fun onChangeGameStatus(model: Model)
    }

    interface Presenter: BasePresenter<View> {

        fun onGoalTop()
        fun onGoalBottom()

        fun onStart()
        fun onReset()
    }

    interface Model {
        val topScore: Long
        val bottomScore: Long
    }
}

