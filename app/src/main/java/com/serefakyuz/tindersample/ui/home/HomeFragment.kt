package com.serefakyuz.tindersample.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.serefakyuz.tindersample.databinding.FragmentHomeBinding
import com.serefakyuz.tindersample.extension.isNetworkAvailable
import com.serefakyuz.tindersample.extension.showNoNetworkDialog
import com.serefakyuz.tindersample.ui.BaseFragment
import com.serefakyuz.tindersample.ui.view.EventListener
import com.serefakyuz.tindersample.ui.view.StackLayoutManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(), EventListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val adapter: CharactersAdapter = CharactersAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(context?.isNetworkAvailable() != true){
            context?.showNoNetworkDialog()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        initViews()
        observeChanges()
        return binding.root
    }


    private fun initViews() {
        binding.recyclerViewCharacters.layoutManager = StackLayoutManager()
        binding.recyclerViewCharacters.setEventListener(this)
        binding.recyclerViewCharacters.adapter = adapter
    }

    private fun observeChanges() {
        viewModel.characterListResponse.observe(viewLifecycleOwner){
           adapter?.submitList(viewModel.characterList)
        }

        viewModel.errorModel.observe(viewLifecycleOwner){
            // Handle API errors
            Log.e("HomeFragment", "observeChanges: $it", )
        }
    }

    override fun onSwiped(position: Int, direction: Int) {
        when(direction){
            StackLayoutManager.SWIPE_LEFT -> {
                //TODO do action
                Log.e("HomeFragment", "NOPE!")
            }
            StackLayoutManager.SWIPE_RIGHT -> {
                //TODO do action
                Log.e("HomeFragment", "LIKE!")
            }
        }
    }

    override fun onChangeHorizontalDrag(direction: Int, percent: Float) {
    }

    override fun onChangeVerticalDrag(direction: Int, percent: Float) {
    }

    override fun onLoadMoreItems() {
        viewModel.loadMore()
    }

}