package com.sarftec.lifehacks.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.sarftec.lifehacks.R
import com.sarftec.lifehacks.utils.NetworkManager
import com.sarftec.lifehacks.view.advertisement.AdCountManager
import com.sarftec.lifehacks.view.advertisement.InterstitialManager
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity() {

    protected val adRequestBuilder: AdRequest by lazy {
        AdRequest.Builder().build()
    }

    protected var interstitialManager: InterstitialManager? = null

    @Inject
    lateinit var networkManager: NetworkManager

    protected open fun canShowInterstitial() : Boolean = false

    protected open fun createAdCounterManager() : AdCountManager {
        return AdCountManager(listOf(1, 4, 3))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Load interstitial if required by extending activity
        if(!canShowInterstitial()) return
        interstitialManager = InterstitialManager(
            this,
            getString(R.string.admob_interstitial_id),
            networkManager,
            createAdCounterManager(),
            adRequestBuilder
        )
        interstitialManager?.load()
    }


    protected fun <T> navigateTo(
        klass: Class<T>,
        finish: Boolean = false,
        slideIn: Int = R.anim.slide_in_right,
        slideOut: Int = R.anim.slide_out_left,
        bundle: Bundle? = null
    ) {
        val intent = Intent(this, klass).also {
            it.putExtra(ACTIVITY_BUNDLE, bundle)
        }
        startActivity(intent)
        if (finish) finish()
        overridePendingTransition(slideIn, slideOut)
    }

    protected fun <T> navigateToWithParcel(
        klass: Class<T>,
        finish: Boolean = false,
        slideIn: Int = R.anim.slide_in_right,
        slideOut: Int = R.anim.no_anim, //slideOut: Int = R.anim.slide_out_left,
        parcel: Parcelable? = null
    ) {
        val intent = Intent(this, klass).also {
            it.putExtra(ACTIVITY_BUNDLE, parcel)
        }
        startActivity(intent)
        if (finish) finish()
        overridePendingTransition(slideIn, slideOut)
    }

    protected fun <T : Parcelable> getParcelFromIntent(intent: Intent) : T? {
        return intent.getParcelableExtra(ACTIVITY_BUNDLE)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.no_anim, R.anim.slide_out_right)
        //overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    companion object {
        const val ACTIVITY_BUNDLE = "activity_bundle"
    }
}