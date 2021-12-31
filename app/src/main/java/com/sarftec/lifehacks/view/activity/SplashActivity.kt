package com.sarftec.lifehacks.view.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.sarftec.lifehacks.data.DatabaseSetup
import com.sarftec.lifehacks.data.local.image.LocalAssets
import com.sarftec.lifehacks.databinding.ActivitySplashBinding
import com.sarftec.lifehacks.view.file.loadImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity :  BaseActivity() {

    @Inject
    lateinit var databaseSetup: DatabaseSetup

    @Inject
    lateinit var localAssets: LocalAssets

    private val layoutBinding by lazy {
        ActivitySplashBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutBinding.root)
        layoutBinding.splashLogo.loadImage(this, localAssets.getLogo())
        lifecycleScope.launchWhenCreated {
            databaseSetup.setupDatabase()
            delay(TimeUnit.SECONDS.toMillis(3))
            navigateTo(MainActivity::class.java, finish = true)
        }
    }
}