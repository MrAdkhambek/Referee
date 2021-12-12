package r2.llc.referee.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import r2.llc.referee.R
import r2.llc.referee.databinding.FragmentMainBinding


@AndroidEntryPoint
class MainFragment : Fragment() {

    private val vm: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm
            .networkLiveData
            .observe(viewLifecycleOwner) { isOnline ->

            }

        vm
            .networkFlow
            .onEach { isOnline ->
                binding.networkStatus.text = getString(R.string.network, if (isOnline) "ON" else "OFF")
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}