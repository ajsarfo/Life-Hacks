package com.sarftec.lifehacks.view.activity

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.sarftec.lifehacks.R
import com.sarftec.lifehacks.databinding.ActivityDetailBinding
import com.sarftec.lifehacks.domain.model.Hack
import com.sarftec.lifehacks.utils.Event
import com.sarftec.lifehacks.view.advertisement.RewardVideoManager
import com.sarftec.lifehacks.view.dialog.LoadingDialog
import com.sarftec.lifehacks.view.file.*
import com.sarftec.lifehacks.view.handler.ReadWriteHandler
import com.sarftec.lifehacks.view.handler.ToolingHandler
import com.sarftec.lifehacks.view.parcel.ListToDetail
import com.sarftec.lifehacks.view.recycler.adapter.DetailItemAdapter
import com.sarftec.lifehacks.view.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity() {

    private val layoutBinding by lazy {
        ActivityDetailBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel by viewModels<DetailViewModel>()

    private val detailAdapter by lazy {
        DetailItemAdapter()
    }

    private lateinit var toolingHandler: ToolingHandler
    private lateinit var readWriteHandler: ReadWriteHandler

    private val rewardVideoManager by lazy {
        RewardVideoManager(
            this,
            R.string.admob_reward_video_id,
            adRequestBuilder,
            networkManager
        )
    }

    private val loadingDialog by lazy {
        LoadingDialog(this, layoutBinding.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutBinding.root)
        readWriteHandler = ReadWriteHandler(this)
        toolingHandler = ToolingHandler(this, readWriteHandler)
        getParcelFromIntent<ListToDetail>(intent)?.let {
            viewModel.setParcel(it)
        }
        setupViewPager()
        setupButtonListeners()
        viewModel.screenState.observe(this) {
            observeState(it)
        }
        viewModel.background.observe(this) {
         observeBackground(it)
        }
        viewModel.fetchHacks()
    }

    private fun observeBackground(event: Event<Uri>) {
        event.getIfNotHandled()?.let {
            layoutBinding.background.loadImage(this, it)
        }
    }

    private fun observeState(state: DetailViewModel.ScreenState) {
        when(state) {
            is DetailViewModel.ScreenState.Loading -> {
                layoutBinding.parent.visibility = View.GONE
            }
            is DetailViewModel.ScreenState.Result -> {
                layoutBinding.parent.visibility = View.VISIBLE
                detailAdapter.submitData(state.hacks)
                layoutBinding.viewPager.setCurrentItem(state.position, false)
            }
            is DetailViewModel.ScreenState.Error -> {
                toast(state.message)
            }
        }
    }

    private fun setupButtonListeners() {
        layoutBinding.bottomLayout.detailBookmark.setOnClickListener {
            viewModel.getCurrentHack()?.let { hack ->
                hack.isFavorite = !hack.isFavorite
                setFavorite(hack)
                viewModel.bookmarkHack(hack)
            }
        }
        layoutBinding.bottomLayout.detailChange.setOnClickListener {
            viewModel.generateRandomBackground()
        }
        layoutBinding.bottomLayout.detailCopy.setOnClickListener {
            viewModel.getCurrentHack()?.let {
                copy(it.message, "Copy")
                toast("Copied to Clipboard")
            }
        }
        layoutBinding.bottomLayout.detailShare.setOnClickListener {
            viewModel.getCurrentHack()?.let {
                share(it.message, "Share")
            }
        }
        layoutBinding.bottomLayout.detailDownload.setOnClickListener {
            loadingDialog.show()
           rewardVideoManager.showRewardVideo {
               layoutBinding.captureFrame.toBitmap {
                   loadingDialog.dismiss()
                   toolingHandler.saveImage(it)
               }
           }
        }
    }

    private fun setFavorite(hack: Hack) {
        layoutBinding.bottomLayout.bookmarkIcon.setImageResource(
            if(hack.isFavorite) R.drawable.ic_bookmark else R.drawable.ic_un_bookmark
        )
    }

    private fun setupViewPager() {
        layoutBinding.viewPager.adapter = detailAdapter
        layoutBinding.viewPager.registerOnPageChangeCallback(
           object : ViewPager2.OnPageChangeCallback() {
               override fun onPageSelected(position: Int) {
                   viewModel.setCurrentPosition(position)
                   viewModel.getHackAtPosition(position)?.let {
                       setFavorite(it)
                   }
                   super.onPageSelected(position)
               }
            }
        )
    }
}