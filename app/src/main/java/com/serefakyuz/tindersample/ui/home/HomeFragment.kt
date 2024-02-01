package com.serefakyuz.tindersample.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.serefakyuz.tindersample.databinding.FragmentHomeBinding
import com.serefakyuz.tindersample.ui.BaseFragment
import com.serefakyuz.tindersample.ui.view.StackLayoutManager
import com.serefakyuz.tindersample.ui.view.SwipeListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(), SwipeListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val adapter: CharactersAdapter = CharactersAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        binding.recyclerViewCharacters.setSwipeListener(this)
        binding.recyclerViewCharacters.adapter = adapter
    }

    private fun observeChanges() {
        viewModel.characterListResponse.observe(viewLifecycleOwner){
           adapter?.submitList(it)
        }
    }

    override fun onSwiped(position: Int, direction: Int) {
    }

    override fun onChangeHorizontalDrag(direction: Int, percent: Float) {
    }

    override fun onChangeVerticalDrag(direction: Int, percent: Float) {
    }


}