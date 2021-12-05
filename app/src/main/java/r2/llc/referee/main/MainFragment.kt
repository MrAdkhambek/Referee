package r2.llc.referee.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.adam.leo.recycler.setupAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import r2.llc.referee.databinding.FragmentMainBinding
import r2.llc.referee.databinding.ItemNameBinding


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

        binding.recyclerView.layoutManager = LinearLayoutManager(view.context)
        val adapter = binding.recyclerView.setupAdapter<String, ItemNameBinding>(
            ItemNameBinding::inflate
        ) { itemBinding, _, item ->
            itemBinding.textView.text = item
        }


        vm
            .state
            .onEach { state ->
                when (state) {
                    ResultState.Loading -> {
                        // TODO()
                    }
                    is ResultState.Error -> {
                        // TODO()
                    }
                    is ResultState.Resource -> {
                        adapter.setList(state.model.list)
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}