package com.sarftec.lifehacks.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sarftec.lifehacks.R
import com.sarftec.lifehacks.databinding.ActivityMainBinding
import com.sarftec.lifehacks.view.advertisement.AdCountManager
import com.sarftec.lifehacks.view.advertisement.BannerManager
import com.sarftec.lifehacks.view.advertisement.RewardVideoManager
import com.sarftec.lifehacks.view.dialog.LoadingDialog
import com.sarftec.lifehacks.view.file.moreApps
import com.sarftec.lifehacks.view.file.rateApp
import com.sarftec.lifehacks.view.file.share
import com.sarftec.lifehacks.view.handler.ReadWriteHandler
import com.sarftec.lifehacks.view.handler.ToolingHandler
import com.sarftec.lifehacks.view.listerner.BookmarkFragmentListener
import com.sarftec.lifehacks.view.listerner.HackListListener
import com.sarftec.lifehacks.view.listerner.HomeFragmentListener
import com.sarftec.lifehacks.view.manager.AppReviewManager
import com.sarftec.lifehacks.view.parcel.CategoryToList
import com.sarftec.lifehacks.view.parcel.ListToDetail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), HomeFragmentListener, BookmarkFragmentListener,
    HackListListener {

    private val layoutBinding by lazy {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }

    private val toggle by lazy {
        ActionBarDrawerToggle(
            this,
            layoutBinding.drawer,
            R.string.open_drawer,
            R.string.close_drawer
        )
    }

    private val appReviewManager by lazy {
        AppReviewManager(this)
    }

    private val mainRewardVideoManager by lazy {
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

    private lateinit var toolingHandler: ToolingHandler
    private lateinit var readWriteHandler: ReadWriteHandler

    private var drawerCallback: (() -> Unit)? = null

    override fun canShowInterstitial(): Boolean = true

    override fun createAdCounterManager(): AdCountManager {
        return AdCountManager(listOf(1, 3, 5, 3))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutBinding.root)
        /*************** Admob Configuration ********************/
        BannerManager(this, adRequestBuilder).attachBannerAd(
            getString(R.string.admob_banner_main),
            layoutBinding.mainBanner
        )
        /**********************************************************/
        readWriteHandler = ReadWriteHandler(this)
        toolingHandler = ToolingHandler(this, readWriteHandler)
        setSupportActionBar(layoutBinding.materialToolbar)
        layoutBinding.materialToolbar.setNavigationOnClickListener {
            layoutBinding.drawer.openDrawer(GravityCompat.START)
        }
        setupNavigationDrawer()
        setupDrawerNavigation()
        layoutBinding.bottomNavigation.setupWithNavController(
            getNavController()
        )
        lifecycleScope.launchWhenCreated {
            appReviewManager.init().triggerReview()
        }
    }

    override fun onBackPressed() {
        layoutBinding.drawer.apply {
            if(isDrawerOpen(GravityCompat.START)) closeDrawer(
                GravityCompat.START
            )
            else super.onBackPressed()
        }

    }

    private fun getNavController(): NavController {
        val navHost = supportFragmentManager.findFragmentById(
            R.id.nav_container
        ) as NavHostFragment
        return navHost.navController
    }

    private fun setupNavigationDrawer() {
        layoutBinding.drawer.addDrawerListener(toggle)
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu)
        toggle.syncState()
        layoutBinding.drawer.addDrawerListener(
            object : DrawerLayout.DrawerListener {
                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}

                override fun onDrawerOpened(drawerView: View) {}

                override fun onDrawerClosed(drawerView: View) {
                    drawerCallback?.invoke()
                    drawerCallback = null
                }

                override fun onDrawerStateChanged(newState: Int) {}
            }
        )
    }

    private fun setupDrawerNavigation() {
        layoutBinding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    layoutBinding.drawer.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.money_saver -> {
                    navigateFromDrawer {
                        navigateToWithParcel(
                            HackListActivity::class.java,
                            parcel = CategoryToList("Money Saver", 4)
                        )
                    }
                    true
                }
                R.id.survival -> {
                    navigateFromDrawer {
                        navigateToWithParcel(
                            HackListActivity::class.java,
                            parcel = CategoryToList("Survival", 7)
                        )
                    }
                    true
                }
                R.id.share -> {
                    navigateFromDrawer {
                        share(
                            "${getString(R.string.app_share_message)}\n\nhttps://play.google.com/store/apps/details?id=${packageName}",
                            "Share"
                        )
                    }
                    true
                }
                R.id.rate_us -> {
                    navigateFromDrawer {
                        rateApp()
                    }
                    true
                }
                R.id.more_apps -> {
                    navigateFromDrawer {
                        moreApps()
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun navigateFromDrawer(callback: () -> Unit) {
        drawerCallback = callback
        layoutBinding.drawer.closeDrawer(GravityCompat.START)
    }

    override fun navigateToList(parcel: CategoryToList) {
        navigateToWithParcel(HackListActivity::class.java, parcel = parcel)
    }

    override fun navigateToDetail(parcel: ListToDetail) {
        navigateToWithParcel(DetailActivity::class.java, parcel = parcel)
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
       return mainRewardVideoManager
    }
}