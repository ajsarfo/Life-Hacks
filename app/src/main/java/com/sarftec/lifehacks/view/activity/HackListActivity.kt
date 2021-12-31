package com.sarftec.lifehacks.view.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sarftec.lifehacks.R
import com.sarftec.lifehacks.databinding.ActivityHackListBinding
import com.sarftec.lifehacks.domain.model.Hack
import com.sarftec.lifehacks.view.advertisement.AdCountManager
import com.sarftec.lifehacks.view.advertisement.BannerManager
import com.sarftec.lifehacks.view.advertisement.RewardVideoManager
import com.sarftec.lifehacks.view.dialog.LoadingDialog
import com.sarftec.lifehacks.view.file.toast
import com.sarftec.lifehacks.view.handler.ReadWriteHandler
import com.sarftec.lifehacks.view.handler.ToolingHandler
import com.sarftec.lifehacks.view.listerner.HackListListener
import com.sarftec.lifehacks.view.parcel.CategoryToList
import com.sarftec.lifehacks.view.parcel.ListToDetail
import com.sarftec.lifehacks.view.recycler.adapter.HackListItemAdapter
import com.sarftec.lifehacks.view.viewmodel.BaseHackListViewModel
import com.sarftec.lifehacks.view.viewmodel.HackListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HackListActivity : BaseActivity(), HackListListener {

    private val layoutBinding by lazy {
        ActivityHackListBinding.inflate(
            layoutInflater
        )
    }

    private val viewModel by viewModels<HackListViewModel>()

    private lateinit var toolingHandler: ToolingHandler
    private lateinit var readWriteHandler: ReadWriteHandler

    private val hackListAdapter by lazy {
        HackListItemAdapter(this, this, viewModel) { hack, position ->
           navigateToDetail(hack, position)
        }
    }

    private val listRewardVideoManager by lazy {
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


    override fun canShowInterstitial(): Boolean = true

    override fun createAdCounterManager(): AdCountManager {
        return AdCountManager(listOf(2, 3, 4))
    }

    override fun onResume() {
        super.onResume()
        viewModel.handleRebind()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutBinding.root)
        /*************** Admob Configuration ********************/
        BannerManager(this, adRequestBuilder).attachBannerAd(
            getString(R.string.admob_banner_list),
            layoutBinding.mainBanner
        )
        /**********************************************************/
        readWriteHandler = ReadWriteHandler(this)
        toolingHandler = ToolingHandler(this, readWriteHandler)
        getParcelFromIntent<CategoryToList>(intent)?.let {
            viewModel.setParcel(it)
        }
        setupToolbar()
        setupAdapter()
        viewModel.screenState.observe(this) {
            observeState(it)
        }
        viewModel.rebindList.observe(this) { event ->
            event.getIfNotHandled()?.let { hackListAdapter.rebindHacks(it) }
        }
        viewModel.fetchHacks()
    }

    private fun navigateToDetail(hack: Hack, position: Int) {
        interstitialManager?.showAd {
            navigateToWithParcel(
                DetailActivity::class.java,
                parcel = ListToDetail(
                    hack.categoryId,
                    viewModel.getCategory() ?: "",
                    position,
                    viewModel.getRandomGeneratorId(),
                    ListToDetail.FROM_LIST
                )
            )
        }
    }

    private fun setupAdapter() {
        layoutBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@HackListActivity)
            adapter = hackListAdapter
        }
    }

    private fun setupToolbar() {
        viewModel.getCategory()?.let {
            layoutBinding.materialToolbar.title = it
            layoutBinding.materialToolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }

    private fun observeState(state: BaseHackListViewModel.ScreenState) {
        when (state) {
            is BaseHackListViewModel.ScreenState.Loading -> {
                showProgress()
            }
            is BaseHackListViewModel.ScreenState.Result -> {
                showRecyclerView()
                hackListAdapter.submitData(state.hacks)
            }
            is BaseHackListViewModel.ScreenState.Error -> {
                showError()
                toast(state.message)
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

    override fun toolingHandler(): ToolingHandler {
        return toolingHandler
    }

    override fun readWriteHandler(): ReadWriteHandler {
        return readWriteHandler
    }

    override fun showLoadingDialog(isShown: Boolean) {
        if(isShown) loadingDialog.show() else loadingDialog.dismiss()
    }

    override fun getRewardVideoManager(): RewardVideoManager {
        return listRewardVideoManager
    }
}