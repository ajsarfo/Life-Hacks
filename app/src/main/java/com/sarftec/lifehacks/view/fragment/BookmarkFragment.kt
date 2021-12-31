package com.sarftec.lifehacks.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sarftec.lifehacks.data.local.image.LocalAssets
import com.sarftec.lifehacks.databinding.FragmentBookmarkBinding
import com.sarftec.lifehacks.view.file.loadImage
import com.sarftec.lifehacks.view.file.toast
import com.sarftec.lifehacks.view.listerner.BookmarkFragmentListener
import com.sarftec.lifehacks.view.listerner.HackListListener
import com.sarftec.lifehacks.view.parcel.ListToDetail
import com.sarftec.lifehacks.view.recycler.adapter.BookmarkItemAdapter
import com.sarftec.lifehacks.view.viewmodel.BaseHackListViewModel
import com.sarftec.lifehacks.view.viewmodel.BookmarkViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private lateinit var layoutBinding: FragmentBookmarkBinding

    private lateinit var hackListListener: HackListListener
    private lateinit var fragmentListener: BookmarkFragmentListener

    private val viewModel by viewModels<BookmarkViewModel>()

    @Inject
    lateinit var localAsset: LocalAssets

    private val bookmarkAdapter by lazy {
        BookmarkItemAdapter(requireContext(), hackListListener, viewModel) { _, position ->
            navigateToDetail(position)
        }
    }

    private fun navigateToDetail(position: Int) {
        fragmentListener.navigateToDetail(
            ListToDetail(
                0,
                "Favorite Hacks",
                position,
                0,
                ListToDetail.FROM_BOOKMARKS
            )
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        hackListListener = context as HackListListener
        fragmentListener = context as BookmarkFragmentListener
    }

    override fun onResume() {
        super.onResume()
        viewModel.handleRebind()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        layoutBinding = FragmentBookmarkBinding.inflate(
            inflater,
            container,
            false
        )
        setupAdapter()
        viewModel.screenState.observe(viewLifecycleOwner) {
            observeState(it)
        }
        viewModel.itemRemoved.observe(viewLifecycleOwner) { event ->
            event.getIfNotHandled()?.let {
                bookmarkAdapter.removeHackAtPosition(it)
            }
        }
        viewModel.fetchHacks()
        layoutBinding.splashLogo.loadImage(layoutBinding.root, localAsset.getLogo())
        return layoutBinding.root
    }

    private fun observeState(state: BaseHackListViewModel.ScreenState) {
        when (state) {
            is BaseHackListViewModel.ScreenState.Loading -> {
            }
            is BaseHackListViewModel.ScreenState.Result -> {
                layoutBinding.nothingToShow.visibility =
                    if (state.hacks.isEmpty()) View.VISIBLE
                    else View.GONE
                bookmarkAdapter.submitData(state.hacks)
            }
            is BaseHackListViewModel.ScreenState.Error -> {
                requireContext().toast(state.message)
            }
        }
    }

    private fun setupAdapter() {
        layoutBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = bookmarkAdapter
        }
    }
}