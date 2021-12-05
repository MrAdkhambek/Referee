package r2.llc.referee.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import r2.llc.referee.Screens
import r2.llc.referee.app.App
import r2.llc.referee.databinding.FragmentMainBinding
import r2.llc.referee.main.mvp.MainContract
import r2.llc.referee.main.mvp.MainPresenterImpl


class MainFragment : Fragment(), MainContract.View {

    private lateinit var binding: FragmentMainBinding
    private lateinit var presenter: MainContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        presenter = MainPresenterImpl()
        presenter.attachView(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topBar.setOnClickListener {
            presenter.onGoalTop()
        }

        binding.bottomBar.setOnClickListener {
            presenter.onGoalBottom()
        }

        binding.playButton.setOnClickListener {
            presenter.onStart()
        }
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun updateScore(model: MainContract.Model) {
        binding.topScoreTextView.text = model.topScore.toString()
        binding.bottomScoreTextView.text = model.bottomScore.toString()
    }

    override fun updateTimer(time: Long) {
        val sec = time % 60
        val min = time / 60
        binding.timerTextView.text = String.format("%d : %d", min, sec)
    }

    override fun showResult(model: MainContract.Model) {
        val winner = if (model.topScore > model.bottomScore) {
            "winner is top command"
        } else "winner is bottom command"

        App.INSTANCE.router.navigateTo(Screens.Result(winner))
//        Snackbar.make(
//            binding.root,
//            winner,
//            Snackbar.LENGTH_INDEFINITE
//        ).apply {
//            setAction("Reset") {
//                presenter.onReset()
//            }
//        }.show()
    }

    override fun onChangeGameStatus(model: MainContract.Model) {
        binding.playButton.isVisible = !model.isStarted
        binding.timerTextView.isVisible = model.isStarted
    }
}