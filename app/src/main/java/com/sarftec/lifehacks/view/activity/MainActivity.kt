package com.sarftec.lifehacks.view.activity

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.sarftec.lifehacks.R
import com.sarftec.lifehacks.databinding.ActivityMainBinding
import com.sarftec.lifehacks.view.listerner.HomeFragmentListener
import com.sarftec.lifehacks.view.parcel.CategoryToList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), HomeFragmentListener {

    private val layoutBinding by lazy {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutBinding.root)
        layoutBinding.bottomNavigation.setupWithNavController(
            getNavController()
        )
    }

    private fun getNavController(): NavController {
        val navHost = supportFragmentManager.findFragmentById(
            R.id.nav_container
        ) as NavHostFragment
        return navHost.navController
    }

    override fun navigateToList(parcel: CategoryToList) {
        navigateToWithParcel(HackListActivity::class.java, parcel = parcel)
    }
}