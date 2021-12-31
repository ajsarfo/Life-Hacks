package com.sarftec.lifehacks.view.activity

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.sarftec.lifehacks.R

abstract class BaseActivity : AppCompatActivity() {

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