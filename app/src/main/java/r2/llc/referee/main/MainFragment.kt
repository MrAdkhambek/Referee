package r2.llc.referee.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import r2.llc.referee.databinding.FragmentMainBinding


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

        vm.isStartFlow
            .onEach { isStarted ->
                binding.playButton.isVisible = !isStarted
                binding.timerTextView.isVisible = isStarted
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        vm.resultFlow
            .onEach { model ->
                val winner = if (model.topScore > model.bottomScore) {
                    "winner is top command"
                } else "winner is bottom command"

                Snackbar.make(
                    binding.root,
                    winner,
                    Snackbar.LENGTH_INDEFINITE
                ).apply {
                    setAction("Reset") {
                        vm.onReset()
                    }
                }.show()
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        vm.scoreFlow
            .onEach { model ->
                binding.topScoreTextView.text = model.topScore.toString()
                binding.bottomScoreTextView.text = model.bottomScore.toString()
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        vm.timerFlow
            .onEach { time ->
                val sec = time % 60
                val min = time / 60
                binding.timerTextView.text = String.format("%d : %d", min, sec)
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}