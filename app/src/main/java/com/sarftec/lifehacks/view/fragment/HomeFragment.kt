package com.sarftec.lifehacks.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sarftec.lifehacks.databinding.FragmentHomeBinding
import com.sarftec.lifehacks.view.file.toast
import com.sarftec.lifehacks.view.listerner.HomeFragmentListener
import com.sarftec.lifehacks.view.parcel.CategoryToList
import com.sarftec.lifehacks.view.recycler.adapter.CategoryItemAdapter
import com.sarftec.lifehacks.view.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var layoutBinding: FragmentHomeBinding

    private lateinit var listener: HomeFragmentListener

    private val viewModel by viewModels<HomeViewModel>()

    private val categoryAdapter by lazy {
        CategoryItemAdapter(lifecycleScope, viewModel) {
            Log.v("TAG", "category id => ${it.id}")
            listener.navigateToList(
                CategoryToList(it.category, it.id)
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as HomeFragmentListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        layoutBinding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )
        setupAdapter()
        viewModel.screenState.observe(viewLifecycleOwner) {
            observeState(it)
        }
        viewModel.fetchCategories()
        return layoutBinding.root
    }

    private fun observeState(state: HomeViewModel.ScreenState) {
        when(state) {
            is HomeViewModel.ScreenState.Loading -> {
                showProgress()
            }
            is HomeViewModel.ScreenState.Result -> {
                showRecyclerView()
                categoryAdapter.submitData(state.categories)
            }
            is HomeViewModel.ScreenState.Error -> {
                showError()
                requireContext().toast(state.message)
            }
        }
    }

    private fun showRecyclerView() {
        layoutBinding.apply {
            progress.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun showProgress() {
        layoutBinding.apply {
            progress.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
    }

    private fun showError() {
        layoutBinding.apply {
            progress.visibility = View.GONE
            recyclerView.visibility = View.GONE
        }
    }

    private fun setupAdapter() {
        layoutBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = categoryAdapter
        }
    }
}