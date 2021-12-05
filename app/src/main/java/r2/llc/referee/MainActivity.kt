package r2.llc.referee

import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import r2.llc.referee.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var topScore: Int = 0
    private var bottomScore: Int = 0

    private var isStart: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.topBar.setOnClickListener {
            topScore++
            binding.topScoreTextView.text = topScore.toString()
        }

        binding.bottomBar.setOnClickListener {
            bottomScore++
            binding.bottomScoreTextView.text = bottomScore.toString()
        }

        binding.timerTextView.isVisible = false
        binding.playButton.setOnClickListener {
            isStart = true
            binding.playButton.isVisible = false
            binding.timerTextView.isVisible = true
            startTimer()
        }
    }

    private fun startTimer() {
        object : CountDownTimer(60 * 1000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                val sec = 60 - (millisUntilFinished / 1000) % 60
                val min = millisUntilFinished / (60 * 1000L)
                binding.timerTextView.text = String.format("%d : %d", min, sec)
            }

            override fun onFinish() {
                isStart = false
                showSnac()
            }
        }.start()
    }

    private fun showSnac() {
        val winner = if (topScore > bottomScore) {
            "winner is top command"
        } else "winner is bottom command"

        Snackbar.make(
            binding.root,
            winner,
            Snackbar.LENGTH_INDEFINITE
        ).apply {
            setAction("Reset") {
                topScore = 0
                bottomScore = 0

                binding.playButton.isVisible = true
                binding.timerTextView.isVisible = false

                binding.topScoreTextView.text = topScore.toString()
                binding.bottomScoreTextView.text = bottomScore.toString()
            }
        }.show()
    }
}