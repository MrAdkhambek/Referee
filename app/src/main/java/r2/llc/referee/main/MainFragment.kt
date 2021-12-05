package r2.llc.referee.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import r2.llc.referee.databinding.FragmentMainBinding
import r2.llc.referee.main.mvp.MainContract


class MainFragment : Fragment(){

    private lateinit var vm: MainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm = ViewModelProvider(this)[MainViewModel::class.java]

        binding.topBar.setOnClickListener {
            vm.onGoalTop()
        }

        binding.bottomBar.setOnClickListener {
            vm.onGoalBottom()
        }

        binding.playButton.setOnClickListener {
            vm.onStart()
        }

        vm.isStartLiveData
            .observe(viewLifecycleOwner, ::onChangeGameStatus)

        vm.resultLiveData
            .observe(viewLifecycleOwner, ::showResult)

        vm.scoreLiveData
            .observe(viewLifecycleOwner, ::updateScore)

        vm.timerLiveData
            .observe(viewLifecycleOwner, ::updateTimer)
    }


    private fun updateScore(model: MainContract.Model) {
        binding.topScoreTextView.text = model.topScore.toString()
        binding.bottomScoreTextView.text = model.bottomScore.toString()
    }

    private fun updateTimer(time: Long) {
        val sec = time % 60
        val min = time / 60
        binding.timerTextView.text = String.format("%d : %d", min, sec)
    }

    private fun showResult(model: MainContract.Model) {
        val winner = if (model.topScore > model.bottomScore) {
            "winner is top command"
        } else "winner is bottom command"

//        App.INSTANCE.router.navigateTo(Screens.Result(winner))
        Snackbar.make(
            binding.root,
            winner,
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("Reset") {
                vm.onReset()
            }
        }.show()
    }

    private fun onChangeGameStatus(isStarted: Boolean) {
        binding.playButton.isVisible = !isStarted
        binding.timerTextView.isVisible = isStarted
    }
}